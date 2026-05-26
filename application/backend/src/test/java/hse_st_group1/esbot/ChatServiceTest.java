package hse_st_group1.esbot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import hse_st_group1.esbot.model.Message;
import hse_st_group1.esbot.model.QuizEvaluation;
import hse_st_group1.esbot.model.QuizRequest;
import hse_st_group1.esbot.model.Session;
import hse_st_group1.esbot.model.User;
import hse_st_group1.esbot.model.QuizRequest.Difficulty;
import hse_st_group1.esbot.repository.MessageRepository;
import hse_st_group1.esbot.repository.QuizAnswerRepository;
import hse_st_group1.esbot.repository.QuizEvaluationRepository;
import hse_st_group1.esbot.repository.QuizItemRepository;
import hse_st_group1.esbot.repository.QuizRequestRepository;
import hse_st_group1.esbot.repository.SessionRepository;
import hse_st_group1.esbot.repository.UserRepository;
import hse_st_group1.esbot.services.AIService;
import hse_st_group1.esbot.services.ChatService;

@DataJpaTest
class ChatServiceTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private QuizRequestRepository quizRequestRepository;
    @Autowired
    private QuizItemRepository quizItemRepository;
    @Autowired
    private QuizAnswerRepository quizAnswerRepository;
    @Autowired
    private QuizEvaluationRepository quizEvaluationRepository;
    @Autowired
    private SessionRepository sessionRepository;
    @MockitoBean
    private AIService aiService;

    private User user;
    private Session session;
    private Message message;

    @BeforeEach
    void setUp(){
        user = new User();
        user.setUserName("Tron");
        userRepository.save(user);
    }
    
    @Test
    void createNewSessionTest(){
        ChatService chatService = new ChatService(sessionRepository, messageRepository, aiService, quizItemRepository, quizRequestRepository, quizAnswerRepository, quizEvaluationRepository);
        this.session = chatService.createNewSession(user);
        assertNotNull(this.session);
        sessionRepository.save(session);
    }

    @Test
    void sendMessageTestAIServiceNotAvailableTest(){
        String messageContent = "Hello AI";
        ChatService chatService = new ChatService(sessionRepository, messageRepository, aiService, quizItemRepository, quizRequestRepository, quizAnswerRepository, quizEvaluationRepository);
        this.session = chatService.createNewSession(user);
        Mockito.when(aiService.isAvailable()).thenReturn(false);
        Mockito.when(aiService.responseString(Mockito.anyString())).thenReturn("Hello from ESbot");
        try{
            this.message = chatService.sendMessage(this.session, messageContent);
            assertNull(this.message);
        }
        catch(AIServiceUnavailableException aiServiceUnavailableException){
            assertNotNull(aiServiceUnavailableException);
            assertEquals("Error: Message service is currently unavailable", aiServiceUnavailableException.getMessage());
        }
    }

    @Test
    void sendMessageTestAIServiceAvailableTest(){
        String messageContent = "Hello AI";
        ChatService chatService = new ChatService(sessionRepository, messageRepository, aiService, quizItemRepository, quizRequestRepository, quizAnswerRepository, quizEvaluationRepository);
        this.session = chatService.createNewSession(user);
        Mockito.when(aiService.isAvailable()).thenReturn(false);
        Mockito.when(aiService.responseString(Mockito.anyString())).thenReturn("Hello from ESbot");
        try{
            this.message = chatService.sendMessage(this.session, messageContent);
            assertNull("Hello from ESbot", message.getMessageContent());
        }
        catch(AIServiceUnavailableException aiServiceUnavailableException){
            assertNotNull(aiServiceUnavailableException);
            assertEquals("Error: Message service is currently unavailable", aiServiceUnavailableException.getMessage());
        }
    }

    @Test
    void sendQuizRequestAIServiceAvailableTest(){
        String quizTopic = "Topic: Software Testing";
        ChatService chatService = new ChatService(sessionRepository, messageRepository, aiService, quizItemRepository, quizRequestRepository, quizAnswerRepository, quizEvaluationRepository);
        this.session = chatService.createNewSession(user);
        Mockito.when(aiService.isAvailable()).thenReturn(true);
        Mockito.when(aiService.createQuestions(Mockito.anyString())).thenReturn(List.of("Question 1: How Many Test Questions?", "Question 2: How Hard Are The Test Questions?", "Question 3: What Is A Rabbyte?"));
        QuizRequest request = null;
        try{
            request = chatService.sendQuizRequest(quizTopic, 3, Difficulty.EASY);
        }
        catch(AIServiceUnavailableException aiServiceUnavailableException){
            assertNull(aiServiceUnavailableException);
        }
        assertNotNull(request);
    }

    @Test
    void sendQuizRequestAIServiceNotAvailableTest(){
        String quizTopic = "Topic: Software Testing";
        ChatService chatService = new ChatService(sessionRepository, messageRepository, aiService, quizItemRepository, quizRequestRepository, quizAnswerRepository, quizEvaluationRepository);
        this.session = chatService.createNewSession(user);
        Mockito.when(aiService.isAvailable()).thenReturn(false);
        Mockito.when(aiService.createQuestions(Mockito.anyString())).thenReturn(List.of("Question 1: How Many Test Questions?", "Question 2: How Hard Are The Test Questions?", "Question 3: What Is A Rabbyte?"));
        QuizRequest request = null;
        try{
            request = chatService.sendQuizRequest(quizTopic, 3, Difficulty.EASY);
        }
        catch(AIServiceUnavailableException aiServiceUnavailableException){
            assertNotNull(aiServiceUnavailableException);
            assertEquals("Error: Quiz service is currently unavailable", aiServiceUnavailableException.getMessage());
        }
        assertNull(request);
    }

    @Test
    void sendQuizRequestWithoutTopicTest(){
        String quizTopic = "";
        ChatService chatService = new ChatService(sessionRepository, messageRepository, aiService, quizItemRepository, quizRequestRepository, quizAnswerRepository, quizEvaluationRepository);
        this.session = chatService.createNewSession(user);
        Mockito.when(aiService.isAvailable()).thenReturn(true);
        Mockito.when(aiService.createQuestions(Mockito.anyString())).thenReturn(List.of("Question 1: How Many Test Questions?", "Question 2: How Hard Are The Test Questions?", "Question 3: What Is A Rabbyte?"));
        QuizRequest request = null;
        try{
            request = chatService.sendQuizRequest(quizTopic, 3, Difficulty.EASY);
        }
        catch(NoQuizTopicProvidedException noQuizTopicProvidedException){
            assertNotNull(noQuizTopicProvidedException);
            assertEquals("Error: No quiz topic provided.", noQuizTopicProvidedException.getMessage());
        }
        assertNull(request);
    }

    @Test
    void evaluateAnswerAIServiceAvailableTest(){
        ChatService chatService = new ChatService(sessionRepository, messageRepository, aiService, quizItemRepository, quizRequestRepository, quizAnswerRepository, quizEvaluationRepository);
        this.session = chatService.createNewSession(user);
        QuizRequest request = null;
        QuizEvaluation evaluation = null;
        Mockito.when(aiService.isAvailable()).thenReturn(true);
        Mockito.when(aiService.createQuestions(Mockito.anyString())).thenReturn(List.of("Question 1: How Many Test Questions?", "Question 2: How Hard Are The Test Questions?", "Question 3: What Is A Rabbyte?"));
        Mockito.when(aiService.evaluateAnswer(Mockito.anyString())).thenReturn("correct");
        request = chatService.sendQuizRequest("Topic: Test", 3, Difficulty.EASY);
        try{
            evaluation = chatService.receiveEvaluation("Test", request.getQuizItems().get(0));
        }
        catch(AIServiceUnavailableException aiServiceUnavailableException){
            assertNull(aiServiceUnavailableException);
        }
        assertNotNull(request);
        assertNotNull(evaluation);
        assertEquals("correct", evaluation.getEvaluation());
    }

    @Test
    void evaluateAnswerAIServiceNotAvailableTest(){
        ChatService chatService = new ChatService(sessionRepository, messageRepository, aiService, quizItemRepository, quizRequestRepository, quizAnswerRepository, quizEvaluationRepository);
        this.session = chatService.createNewSession(user);
        QuizRequest request = null;
        QuizEvaluation evaluation = null;
        Mockito.when(aiService.isAvailable()).thenReturn(true);
        Mockito.when(aiService.createQuestions(Mockito.anyString())).thenReturn(List.of("Question 1: How Many Test Questions?", "Question 2: How Hard Are The Test Questions?", "Question 3: What Is A Rabbyte?"));
        Mockito.when(aiService.evaluateAnswer(Mockito.anyString())).thenReturn("correct");
        request = chatService.sendQuizRequest("Topic: Test", 3, Difficulty.EASY);
        Mockito.when(aiService.isAvailable()).thenReturn(false);
        try{
            evaluation = chatService.receiveEvaluation("Test", request.getQuizItems().get(0));
        }
        catch(AIServiceUnavailableException aiServiceUnavailableException){
            assertNotNull(aiServiceUnavailableException);
            assertEquals("Error: Evaluation service is currently unavailable", aiServiceUnavailableException.getMessage());
        }
        assertNotNull(request);
        assertNull(evaluation);
    }

}
