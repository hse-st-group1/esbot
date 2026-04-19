package hse_st_group1.esbot.feature;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import hse_st_group1.esbot.AIServiceUnavailableException;
import hse_st_group1.esbot.model.*;
import hse_st_group1.esbot.repository.QuizItemRepository;
import hse_st_group1.esbot.repository.QuizRequestRepository;
import hse_st_group1.esbot.repository.SessionRepository;
import hse_st_group1.esbot.repository.UserRepository;
import hse_st_group1.esbot.services.AIService;
import hse_st_group1.esbot.services.MessageService;
import hse_st_group1.esbot.services.QuizRequestService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import jakarta.transaction.Transactional;
import io.cucumber.java.en.Then;
import io.cucumber.java.After;
import io.cucumber.java.Before;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@CucumberContextConfiguration
public class AIFallbackSteps {

    private User user;
    private Session session;
    private Timestamp timestamp;

    private Message message;
    private Message aiMessage;

    private QuizRequest quizRequest;
    private QuizRequest quiz;

    private RuntimeException exception;

    @Autowired
    private MessageService messageService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private QuizRequestService quizRequestService;
    @Autowired
    private QuizRequestRepository quizRequestRepository;
    @Autowired
    private QuizItemRepository quizItemRepository;
    @MockitoBean
    private AIService aiService;

    @Before
    public void startSetup(){
        MockitoAnnotations.openMocks(this.aiService);
    }

    @Given("I have a session")
    public void i_have_a_session(){
        this.user = new User(null, "cucumber_test", null);
        this.user = userRepository.save(user);
        
        timestamp = new Timestamp(System.currentTimeMillis());
        this.session = new Session(null, user, timestamp, timestamp, null, null);
        this.session = sessionRepository.save(session);
    }

    @Given("AI messaging is available")
    public void ai_messaging_is_available(){
        Mockito.when(aiService.isAvailable()).thenReturn(true);
        Mockito.when(aiService.responseString(Mockito.anyString())).thenReturn("Hello from ESbot");
    }

    @Given("AI messaging is not available")
    public void ai_messaging_is_not_available(){
        Mockito.when(aiService.isAvailable()).thenReturn(false);
        Mockito.when(aiService.responseString(Mockito.anyString()))
            .thenReturn(null);
    }

    @Given("AI quiz generation is available")
    public void ai_quiz_gen_is_available(){
        Mockito.when(aiService.isAvailable()).thenReturn(true);
        Mockito.when(aiService.createQuestions(Mockito.anyString())).thenReturn(List.of("Question 1: How Many Test Questions?", "Question 2: How Hard Are The Test Questions?", "Question 3: What Is A Rabbyte?"));
    }

    @Given("AI quiz generation is not available")
    public void ai_quiz_gen_is_not_available(){
        Mockito.when(aiService.isAvailable()).thenReturn(false);
    }

    @When("I send a message")
    public void i_send_a_message(){
        timestamp = new Timestamp(System.currentTimeMillis());
        message = new Message(null, session, null, timestamp, false, "MESSAGE");
        message.setMessageContent("Hello");
        try{
            aiMessage = messageService.sendMessage(message);
        }
        catch(AIServiceUnavailableException serviceUnavailableException){
            exception = serviceUnavailableException;
        }
    }

    @Then("a message gets send back to me")
    public void a_message_gets_send_back_to_me(){
        assertNotNull(aiMessage);
        assertEquals("Hello from ESbot", aiMessage.getMessageContent());
    }

    @When("I send a quizrequest")
    public void i_send_a_quizrequest(){
        quizRequest = new QuizRequest();
        quizRequest.setQuizRequestContent("Topic: Testquiz");
        quizRequest.setSession(session);
        try{
            quiz = quizRequestService.createQuiz(quizRequest);
        }
        catch(AIServiceUnavailableException serviceUnavailableException){
            exception = serviceUnavailableException;
        }
    }

    @Then("a quiz gets send back")
    public void a_quiz_gets_send_back() {
        assertNotNull(quiz);
        List<QuizItem> list = new ArrayList<>(quiz.getQuizItems());
        assertEquals("Question 1: How Many Test Questions?", list.get(0).getQuestion());
        assertEquals("Question 2: How Hard Are The Test Questions?", list.get(1).getQuestion());
        assertEquals("Question 3: What Is A Rabbyte?", list.get(2).getQuestion());
    }

    @Then("a error message gets sent back stating the message service is currently unavailable")
    public void error_message_unavailable(){
        assertEquals("Error: Message service is currently unavailable", exception.getMessage());
    }

    @Then("a error message gets sent back stating the quiz service is currently unavailable")
    public void error_quiz_unavailable(){
        assertEquals("Error: Quiz service is currently unavailable", exception.getMessage());
    }

    @After
    public void cleanDB() {
        quizItemRepository.deleteAll();
        quizRequestRepository.deleteAll();
        sessionRepository.deleteAll();
        userRepository.deleteAll();
    }
}