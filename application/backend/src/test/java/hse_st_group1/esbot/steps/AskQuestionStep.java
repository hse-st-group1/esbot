package hse_st_group1.esbot.steps;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Timestamp;

import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import hse_st_group1.esbot.AIServiceUnavailableException;
import hse_st_group1.esbot.model.*;
import hse_st_group1.esbot.repository.SessionRepository;
import hse_st_group1.esbot.repository.UserRepository;
import hse_st_group1.esbot.services.AIService;
import hse_st_group1.esbot.services.MessageService;

import io.cucumber.java.en.*;
import io.cucumber.java.Before;
import io.cucumber.java.After;
import io.cucumber.spring.CucumberContextConfiguration;
import jakarta.transaction.Transactional;


public class AskQuestionStep{

    private User user;
    private Message question;
    private Message answer;
    private Session session;
    private RuntimeException exception;
    private Timestamp timestamp;


    @Autowired
    private MessageService messageService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private AIService aiService;
 

    @Given("I have a session, too")
    public void i_have_a_session_too(){
        this.user = new User(null, "cucumber_test", null);
        this.user = userRepository.save(user);
        
        timestamp = new Timestamp(System.currentTimeMillis());
        this.session = new Session(null, user, timestamp, timestamp, null, null);
        this.session = sessionRepository.save(session);
    }

    @Given("a Student asks {string}")
    public void student_asks(String questionText){
    Timestamp now = new Timestamp(System.currentTimeMillis());

    this.question = new Message(null, session, questionText , now, false,"question");
}
    @Given("the AI service is available")
    public void ai_service_available() {
        Mockito.when(aiService.isAvailable()).thenReturn(true);
        Mockito.when(aiService.responseString(Mockito.anyString()))
                .thenReturn("The most op Animal is the Rabbit");
    }

    @Given("the AI service is unavailable")
    public void ai_service_unavailable() {
        Mockito.when(aiService.responseString(Mockito.anyString()))
        .thenThrow(new AIServiceUnavailableException("Error: Message service is currently unavailable"));
    }

    @When("student send the question")
    public void send_question() {
        try {
            answer = messageService.sendMessage(question);
        } catch (AIServiceUnavailableException e) {
            exception = e;
        }
    }

    @Then("the response should be {string}")
    public void check_answer(String expected){
        assertNotNull(answer);
        assertEquals(expected, answer.getMessageContent());
    }

    @Then("the error should be {string}")
    public void check_error(String expectedError){
        assertNotNull(exception);
        assertEquals(expectedError, exception.getMessage());
    }

    @After
    public void cleanDB(){
        sessionRepository.deleteAll();
        userRepository.deleteAll();
    }    
}