package hse_st_group1.esbot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatObject;
import static org.assertj.core.api.Assertions.useRepresentation;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.resttestclient.autoconfigure.SpringBootRestTestClientBuilderCustomizer;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import hse_st_group1.esbot.model.Message;
import hse_st_group1.esbot.model.Session;
import hse_st_group1.esbot.model.User;
import hse_st_group1.esbot.repository.MessageRepository;
import hse_st_group1.esbot.repository.QuizAnswerRepository;
import hse_st_group1.esbot.repository.QuizEvaluationRepository;
import hse_st_group1.esbot.repository.QuizItemRepository;
import hse_st_group1.esbot.repository.QuizRequestRepository;
import hse_st_group1.esbot.repository.SessionRepository;
import hse_st_group1.esbot.repository.UserRepository;
import hse_st_group1.esbot.services.AIService;
import hse_st_group1.esbot.services.ChatService;
import io.cucumber.java.Before;

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
    void sendMessageTestAIServiceNotAvailable(){
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
    void sendMessageTestAIServiceAvailable(){
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

    
}
