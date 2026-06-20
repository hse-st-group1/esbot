package hse_st_group1.esbot.steps;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import hse_st_group1.esbot.UnknownUserException;
import hse_st_group1.esbot.model.Session;
import hse_st_group1.esbot.model.User;
import hse_st_group1.esbot.repository.SessionRepository;
import hse_st_group1.esbot.repository.UserRepository;
import hse_st_group1.esbot.services.ChatService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class getSessionsSteps {
    private User user;
    private User testUser;
    private Session session;
    private List<Session> sessionList;

    UnknownUserException exception;
    
    private Session oldSession1;
    private Session oldSession2;
    private Session oldSession3;
    
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private ChatService chatService;


    @Given("I have an account")
    public void i_have_an_account(){
        testUser = new User();
        testUser.setUserName("IAmACucumber");
        assertNotNull(userRepository.save(testUser));
        user = userRepository.findByUserName("IAmACucumber");
        assertEquals(testUser.getUserID(),user.getUserID());
    }

    @Given("I have old sessions")
    public void i_have_old_sessions(){
        oldSession1 = chatService.createNewSession(testUser);
        oldSession2 = chatService.createNewSession(testUser);
        oldSession3 = chatService.createNewSession(testUser);
        assertNotNull(oldSession1);
        assertNotNull(oldSession2);
        assertNotNull(oldSession3);
    }

    @Given("I don't have an account")
    public void i_dont_have_an_account(){
        testUser = null;
        assertNull(testUser);
    }

    @When("I create a new session")
    public void i_create_a_new_session(){
        session = chatService.createNewSession(testUser);
        assertNotNull(session);
    }

    @When("I try to create a new session")
    public void i_try_to_create_a_new_session(){
        try{
            session = chatService.createNewSession(testUser);
        }
        catch(UnknownUserException userException){
            exception = userException;
        }
    }

    @When("I request my old sessions")
    public void i_request_my_old_sessions(){
        sessionList = sessionRepository.findByUser(testUser);
    }

    @Then("I receive a new session")
    public void i_receive_a_new_session(){
        assertNotNull(sessionRepository.findBySessionID(session.getSessionID()));
    }

    @Then("I receive my old sessions")
    public void i_receive_my_old_sessions(){
        assertNotNull(sessionList);
        assertTrue(sessionList.containsAll(sessionList));
    }

    @Then("I receive an unknown user error")
    public void i_receive_an_unknown_user_error(){
        assertNotNull(exception);
        assertEquals("Unknown User: Please create an account", exception.getMessage());
    }
}
