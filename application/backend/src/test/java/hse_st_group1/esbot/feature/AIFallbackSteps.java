package hse_st_group1.esbot.feature;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import org.mockito.Mockito;

import hse_st_group1.esbot.model.*;
import hse_st_group1.esbot.services.MessageService;
import hse_st_group1.esbot.services.QuizRequestService;
import hse_st_group1.esbot.util.UnitTestHelper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.Before;

public class AIFallbackSteps {

    private User user;
    private Session session;
    private MessageService messageService;
    private QuizRequestService quizRequestService;
    private Message message;
    private QuizRequest quizRequest;
    private Message aiMessage;
    private Boolean aiAvailable;

    @Before
    public void setAvailability(){
        aiAvailable = true;
    }

    @Before
    public void serviceMessage() {
        messageService = Mockito.mock(MessageService.class);
    }
    @Before
    public void serviceQuizRequest() {
        quizRequestService = Mockito.mock(QuizRequestService.class);
    }

    @Given("I have a session")
    public void i_have_a_session(){
        this.user = UnitTestHelper.userCreator();
        this.session = UnitTestHelper.sessionCreator(user);
    }

    @When("I send a message")
    public void i_send_a_message(){
        message = UnitTestHelper.createTestMessage(session);
        message.setMessageContent("Hello");

        Message newMessage = UnitTestHelper.createTestMessage(session);
        if(aiAvailable == true){
            newMessage.setMessageContent("Hello from ESbot");
        }else{
            newMessage.setMessageContent("Error: Service currently unavailable");
        }

        Mockito.when(messageService.sendMessage(Mockito.any())).thenReturn(newMessage);

        aiMessage = messageService.sendMessage(message);
    }

    @Then("a message gets send back to me")
    public void a_message_gets_send_back_to_me(){
        assertEquals("Hello from ESbot", aiMessage.getMessageContent());
    }

    @When("I send a quizrequest")
    public void i_send_a_quizrequest(){
        quizRequest = UnitTestHelper.quizRequestCreator(session);
        quizRequest.setQuizRequestContent("Topic: Testquiz");

        QuizItem question1 = new QuizItem(null, quizRequest, null, null, null);
        QuizItem question2 = new QuizItem(null, quizRequest, null, null, null);
        QuizItem question3 = new QuizItem(null, quizRequest, null, null, null);
        question1.setQuestion("Question 1: How Many Test Questions?");
        question2.setQuestion("Question 2: How Hard Are The Test Questions");
        question3.setQuestion("Question 3: What is a rabbit?");

        quizRequest.setQuizItems(new LinkedHashSet<QuizItem>());
        quizRequest.getQuizItems().add(question1);
        quizRequest.getQuizItems().add(question2);
        quizRequest.getQuizItems().add(question3);

        Mockito.when(quizRequestService.sendQuizRequest(Mockito.any())).thenReturn(quizRequest);
    }

    @Then("a quiz gets send back")
    public void a_quiz_gets_send_back() {
        List<QuizItem> items = new ArrayList<QuizItem>(quizRequest.getQuizItems());
        assertEquals("Question 1: How Many Test Questions?", items.get(0).getQuestion());
        assertEquals("Question 2: How Hard Are The Test Questions", items.get(1).getQuestion());
        assertEquals("Question 3: What is a rabbit?", items.get(2).getQuestion());
    }

    @When("AI is not available")
    public void ai_is_not_available(){
        aiAvailable = false;
    }

    @Then("a error message gets sent back stating the service is currently unavailable")
    public void error_message_unavailable(){
        assertEquals("Error: Service currently unavailable", aiMessage.getMessageContent());
    }
}