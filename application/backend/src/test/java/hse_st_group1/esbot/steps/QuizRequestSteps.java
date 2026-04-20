package hse_st_group1.esbot.steps;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.mockito.Mockito;

import hse_st_group1.esbot.model.Message;
import hse_st_group1.esbot.model.QuizItem;
import hse_st_group1.esbot.model.QuizRequest;
import hse_st_group1.esbot.model.Session;
import hse_st_group1.esbot.model.User;
import hse_st_group1.esbot.services.MessageService;
import hse_st_group1.esbot.services.QuizRequestService;
import hse_st_group1.esbot.util.UnitTestHelper;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.But;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class QuizRequestSteps {

    private User user;
    private Session session;
    private MessageService messageService;
    private QuizRequestService quizRequestService;
    private QuizRequest quizRequest;
    private Boolean aiCanProcessRequest;

    private String exampleConcept;
    private int numberOfQuestions;
    private String exampleQuizTopic;

    @Before
    public void setAvailabilityToProcessRequest(){
        aiCanProcessRequest = true;
    }

    @Before
    public void serviceMessage() {
        messageService = Mockito.mock(MessageService.class);
    }

    @Before
    public void serviceQuizRequest() {
        quizRequestService = Mockito.mock(QuizRequestService.class);
    }

    @Given("I have a session where i can request a quiz")
    public void quizRequest_I_have_a_session(){
        this.user = UnitTestHelper.userCreator();
        this.session = UnitTestHelper.sessionCreator(user);
    }
    @And("I have a topic I want to be quizzed on")
    public void i_have_a_topic_i_want_to_be_quizzed_on(){
        this.exampleQuizTopic = "ExampleTopic";
    }
    @And("I have a number of questions i want the quiz to have")
    public void i_have_a_number_of_questions_i_want_the_quiz_to_have(){
        this.numberOfQuestions = 3;
    }

    @When("I send a message requesting a quiz, which contains the topic and the number of questions i want")
    public void i_send_a_message_requesting_a_quiz_and_provide_a_topic_and_the_number_of_questions(){
        quizRequest = new QuizRequest(null, session, null, null); 
        quizRequest.setQuizRequestContent("Create a quiz about " + exampleQuizTopic + " containing " + numberOfQuestions + " questions");

        if (aiCanProcessRequest == true) {
            QuizItem question1 = new QuizItem(null, quizRequest, null, null, null);
            QuizItem question2 = new QuizItem(null, quizRequest, null, null, null);
            QuizItem question3 = new QuizItem(null, quizRequest, null, null, null);
            question1.setQuestion("Content of Question 1");
            question2.setQuestion("Content of Question 2");
            question3.setQuestion("Content of Question 3");

            quizRequest.setQuizItems(new LinkedHashSet<QuizItem>());
            quizRequest.getQuizItems().add(question1);
            quizRequest.getQuizItems().add(question2);
            quizRequest.getQuizItems().add(question3);
        } else{
            QuizItem cannotProcessRequest = new QuizItem(null, quizRequest, null, null, null);
            cannotProcessRequest.setQuestion("Service cannot generate a quiz about this topic. Please choose a different one.");
            quizRequest.setQuizItems(new LinkedHashSet<QuizItem>(Set.of(cannotProcessRequest)));
        }

        Mockito.when(quizRequestService.sendQuizRequest(Mockito.any())).thenReturn(quizRequest);
    }

    @Then("a list of questions should be shown to me")
    public void a_list_of_questions_should_be_shown_to_me(){
        List<QuizItem> items = new ArrayList<QuizItem>(quizRequest.getQuizItems());
        assertEquals(3,items.size() );
        assertEquals("Content of Question 1", items.get(0).getQuestion());
        assertEquals("Content of Question 2", items.get(1).getQuestion());
        assertEquals("Content of Question 3", items.get(2).getQuestion());
    }

    @But("the AI is unable to generate a quiz about the requested topic")
    public void ai_is_unable_to_generate_quiz_about_topic(){
        this.aiCanProcessRequest = false;
    }

    @Then("an error message should be shown stating that the LLM cannot generate a quiz about this topic")
    public void error_should_be_shown_that_llm_cannot_generate_quiz_about_this_topic_and_suggest_alternate_topic(){
        List<QuizItem> items = new ArrayList<QuizItem>(quizRequest.getQuizItems());
        assertEquals("Service cannot generate a quiz about this topic. Please choose a different one.", items.get(0).getQuestion());
    }

    @When("I request a quiz")
    public void i_request_a_quiz(){
        quizRequest = new QuizRequest(null, session, null, null); 
        quizRequest.setQuizRequestContent("Create a quiz containing " + numberOfQuestions + " questions");
    }
    @But("do not provide the topic of the quiz or exercise")
    public void i_request_a_quiz_or_an_exercise_without_providing_a_topic(){
        Mockito.when(quizRequestService.sendQuizRequest(Mockito.any())).thenAnswer(invocation->{ //AI USAGE: Used AI to fix sytax errors so function can be executed instead of returning object
            
            String content = quizRequest.getQuizRequestContent();
            
            // If no topic was provided (-> no "about ...") then LLM should request topic
            if (!content.contains("about")) { //AI USAGE: Asked AI how to check whether request contains topic, given that it is part of a string and not an attribute
                QuizItem topicMissing = new QuizItem(null, quizRequest, null, null, null);
                topicMissing.setQuestion("What should the Quiz be about?");
                quizRequest.setQuizItems(new LinkedHashSet<QuizItem>(Set.of(topicMissing)));
            
            } else { // If topic was provided, proceed as normal
                QuizItem question1 = new QuizItem(null, quizRequest, null, null, null);
                QuizItem question2 = new QuizItem(null, quizRequest, null, null, null);
                QuizItem question3 = new QuizItem(null, quizRequest, null, null, null);
                question1.setQuestion("Content of Question 1");
                question2.setQuestion("Content of Question 2");
                question3.setQuestion("Content of Question 3");

                quizRequest.setQuizItems(new LinkedHashSet<QuizItem>());
                quizRequest.getQuizItems().add(question1);
                quizRequest.getQuizItems().add(question2);
                quizRequest.getQuizItems().add(question3);
            }

            return quizRequest;
        });

        Mockito.when(quizRequestService.sendQuizRequest(Mockito.any())).thenReturn(quizRequest);
 
    }
    
    @Then("the LLM should ask me to clarify the topic of the quiz")
    public void the_LLM_should_ask_me_to_clarify_the_topic (){
        List<QuizItem> items = new ArrayList<QuizItem>(quizRequest.getQuizItems());
        assertEquals(1,items.size() );
        assertEquals("What should the Quiz be about?", items.get(0).getQuestion());
    }
}
