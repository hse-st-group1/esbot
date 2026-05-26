package hse_st_group1.esbot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

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

class ChatServiceTest {

    private UserRepository userRepository;
    private SessionRepository sessionRepository;
    private MessageRepository messageRepository;
    private QuizRequestRepository quizRequestRepository;
    private QuizItemRepository quizItemRepository;
    private QuizAnswerRepository quizAnswerRepository;
    private QuizEvaluationRepository quizEvaluationRepository;

    private User user;
    private AIService aiServiceMock;
    private ChatService chatService;
    private Session session;
    private Message message;

    @BeforeEach
    void setUp(){
        aiServiceMock = Mockito.mock(AIService.class);
        userRepository = Mockito.mock(UserRepository.class);
        sessionRepository = Mockito.mock(SessionRepository.class);
        messageRepository = Mockito.mock(MessageRepository.class);
        quizRequestRepository = Mockito.mock(QuizRequestRepository.class);
        quizItemRepository = Mockito.mock(QuizItemRepository.class);
        quizAnswerRepository = Mockito.mock(QuizAnswerRepository.class);
        quizEvaluationRepository = Mockito.mock(QuizEvaluationRepository.class);
        chatService = new ChatService(sessionRepository, messageRepository, aiServiceMock, quizItemRepository, quizRequestRepository, quizAnswerRepository, quizEvaluationRepository);

        user = new User();
        user.setUserName("Tron");
        userRepository.save(user);
    }
    
    @Test
    void createNewSessionTest(){
        this.session = chatService.createNewSession(user);
        assertNotNull(this.session);
        sessionRepository.save(session);
    }

    @Test
    void sendMessageTestAIServiceAvailable(){
        String messageContent = "Hello AI";
        this.session = chatService.createNewSession(user);
        Mockito.when(aiServiceMock.isAvailable()).thenReturn(true);
        Mockito.when(aiServiceMock.responseString(Mockito.anyString())).thenReturn("Hello from ESbot");
        try{
            this.message = chatService.sendMessage(this.session, messageContent);
            assertEquals("Hello from ESbot", message.getMessageContent());
        }
        catch(AIServiceUnavailableException aiServiceUnavailableException){
            assertNull(aiServiceUnavailableException);
        }
    }
    @Test
    void sendMessageTestAIServiceNotAvailable(){
        String messageContent = "Hello AI";
        this.session = chatService.createNewSession(user);
        Mockito.when(aiServiceMock.isAvailable()).thenReturn(false);
        Mockito.when(aiServiceMock.responseString(Mockito.anyString())).thenReturn("Hello from ESbot");
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
    void sendQuizRequestAIServiceAvailableTest(){
        String quizTopic = "Software Testing";
        this.session = chatService.createNewSession(user);
        Mockito.when(aiServiceMock.isAvailable()).thenReturn(true);
        Mockito.when(aiServiceMock.createQuestions(Mockito.anyString())).thenReturn(List.of("Question 1: How Many Test Questions?", "Question 2: How Hard Are The Test Questions?", "Question 3: What Is A Rabbyte?"));
        QuizRequest request = null;
        try{
            request = chatService.sendQuizRequest(quizTopic, 3, Difficulty.EASY);
        }
        catch(AIServiceUnavailableException aiServiceUnavailableException){
            assertNull(aiServiceUnavailableException);
        }
        assertNotNull(request);
        assertEquals("Topic: Software Testing", request.getQuizRequestContent());
    }
    @Test
    void sendQuizRequestWithoutTopicTest(){
        String quizTopic = "";
        Mockito.when(aiServiceMock.isAvailable()).thenReturn(true);
        Mockito.when(aiServiceMock.createQuestions(Mockito.anyString())).thenReturn(List.of("Question 1: How Many Test Questions?", "Question 2: How Hard Are The Test Questions?", "Question 3: What Is A Rabbyte?"));
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
    void sendQuizRequestAIServiceNotAvailableTest(){
        String quizTopic = "Software Testing";
        Mockito.when(aiServiceMock.isAvailable()).thenReturn(false);
        Mockito.when(aiServiceMock.createQuestions(Mockito.anyString())).thenReturn(List.of("Question 1: How Many Test Questions?", "Question 2: How Hard Are The Test Questions?", "Question 3: What Is A Rabbyte?"));
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
    void evaluateAnswerAIServiceAvailableTest(){
        this.session = chatService.createNewSession(user);
        QuizRequest request = null;
        QuizEvaluation evaluation = null;
        Mockito.when(aiServiceMock.isAvailable()).thenReturn(true);
        Mockito.when(aiServiceMock.createQuestions(Mockito.anyString())).thenReturn(List.of("Question 1: How Many Test Questions?", "Question 2: How Hard Are The Test Questions?", "Question 3: What Is A Rabbyte?"));
        Mockito.when(aiServiceMock.evaluateAnswer(Mockito.anyString())).thenReturn("correct");
        request = chatService.sendQuizRequest("Test", 3, Difficulty.EASY);
        try{
            evaluation = chatService.receiveEvaluation("Test", request.getQuizItems().get(0));
        }
        catch(AIServiceUnavailableException aiServiceUnavailableException){
            assertNull(aiServiceUnavailableException);
        }
        assertEquals("Topic: Test", request.getQuizRequestContent());
        assertNotNull(evaluation);
        assertEquals("correct", evaluation.getEvaluation());
    }
    @Test
    void evaluateAnswerWithEmptyAnswerTest(){
        this.session = chatService.createNewSession(user);
        QuizRequest request = null;
        QuizEvaluation evaluation = null;
        Mockito.when(aiServiceMock.isAvailable()).thenReturn(true);
        Mockito.when(aiServiceMock.createQuestions(Mockito.anyString())).thenReturn(List.of("Question 1: How Many Test Questions?", "Question 2: How Hard Are The Test Questions?", "Question 3: What Is A Rabbyte?"));
        Mockito.when(aiServiceMock.evaluateAnswer(Mockito.anyString())).thenReturn("correct");
        request = chatService.sendQuizRequest("Test", 3, Difficulty.EASY);
        try{
            evaluation = chatService.receiveEvaluation("", request.getQuizItems().get(0));
        }
        catch(AnswerEmptyException answerEmptyException){
            assertNotNull(answerEmptyException);
            assertEquals("Error: Answer provided is empty", answerEmptyException.getMessage());
        }
        assertEquals("Topic: Test", request.getQuizRequestContent());
        assertNotNull(request);
        assertNull(evaluation);
    }
    @Test
    void evaluateAnswerAIServiceNotAvailableTest(){
        this.session = chatService.createNewSession(user);
        QuizRequest request = null;
        QuizEvaluation evaluation = null;
        Mockito.when(aiServiceMock.isAvailable()).thenReturn(true, false);
        Mockito.when(aiServiceMock.createQuestions(Mockito.anyString())).thenReturn(List.of("Question 1: How Many Test Questions?", "Question 2: How Hard Are The Test Questions?", "Question 3: What Is A Rabbyte?"));
        Mockito.when(aiServiceMock.evaluateAnswer(Mockito.anyString())).thenReturn("correct");
        request = chatService.sendQuizRequest("Topic: Test", 3, Difficulty.EASY);
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
