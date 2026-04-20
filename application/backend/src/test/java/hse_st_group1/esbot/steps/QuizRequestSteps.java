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

    @Given("I have a session and I have a topic I want to be quizzed on")
    public void i_have_a_session_and_a_topic_i_want_to_be_quizzed_on(){
        this.user = UnitTestHelper.userCreator();
        this.session = UnitTestHelper.sessionCreator(user);
        this.numberOfQuestions = 3;
        this.exampleQuizTopic = "ExampleTopic";
    }

    @When("I send a message requesting a quiz and provide the topic I want to be quizzed about and provide the number of questions")
    public void i_send_a_message_requesting_a_quiz_and_provide_a_topic_and_the_number_of_questions(){
        quizRequest = new QuizRequest(null, session, null, null); 
        quizRequest.setQuizRequestContent("Create a quiz about " + exampleQuizTopic + " containing " + numberOfQuestions + " questions");

        if (aiCanProcessRequest = true) {
             QuizItem question1 = new QuizItem(null, quizRequest, null, null, null);
            QuizItem question2 = new QuizItem(null, quizRequest, null, null, null);
            QuizItem question3 = new QuizItem(null, quizRequest, null, null, null);
            question1.setQuestion("Content of Question 1");
            question2.setQuestion("Content of Question 2");
            question3.setQuestion("Content of Question 3");

            quizRequest.setQuizItems(new LinkedHashSet<QuizItem>(Set.of(question1, question2, question3)));
        } else{
            QuizItem cannotProcessRequest = new QuizItem(null, quizRequest, null, null, null);
            cannotProcessRequest.setQuestion("Service cannot generate a quiz about this topic. Please choose a different one.");
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

    @When("the AI is unable to generate a quiz about the requested topic")
    public void ai_is_unable_to_generate_quiz_about_topic(){
        this.aiCanProcessRequest = false;
    }

    @Then("an error message should be shown stating that the LLM cannot generate a quiz about this topic and it should suggest a related topic I can request a quiz about instead")
    public void error_should_be_shown_that_llm_cannot_generate_quiz_about_this_topic_and_suggest_alternate_topic(){
        List<QuizItem> items = new ArrayList<QuizItem>(quizRequest.getQuizItems());
        assertEquals("Service cannot generate a quiz about this topic. Please choose a different one.", items.get(0).getQuestion());
    }

    @Given ("I have a session and I have a concept I want to exercise my understanding of to test it")
    public void i_have_a_session_and_want_to_generate_an_exercise_about_a_concept(){
        this.user = UnitTestHelper.userCreator();
        this.session = UnitTestHelper.sessionCreator(user);
        this.exampleConcept = "ExampleConcept";
    }

    @When ("I send a request to generate an exercise and I provide the concept the exercise should be about")
    public void i_request_an_exercise_about_a_concept(){
        quizRequest = new QuizRequest(null, session, null, null);
        quizRequest.setQuizRequestContent("Create an exercise about " + exampleConcept );

        QuizItem exercise = new QuizItem(null, quizRequest, null, null, null);
        exercise.setQuestion("Example of Exercise about " + exampleConcept);

        quizRequest.setQuizItems(new LinkedHashSet<QuizItem>(Set.of(exercise)));

        Mockito.when(quizRequestService.sendQuizRequest(Mockito.any())).thenReturn(quizRequest);
    }

    @Then("an exercise should be shown to me")
    public void an_exercise_should_be_shown_to_me(){
        List<QuizItem> items = new ArrayList<QuizItem>(quizRequest.getQuizItems());
        assertEquals(1,items.size() );
        assertEquals("Example of Exercise about " + exampleConcept, items.get(0).getQuestion());
    }

    @Given("I have a session")
    public void i_have_a_session(){
        this.user = UnitTestHelper.userCreator();
        this.session = UnitTestHelper.sessionCreator(user);
    }
    @When("I request a quiz or an exercise but do not provide the topic of the quiz or exercise")
    public void i_request_a_quiz_or_an_exercise_without_providing_a_topic(){
        quizRequest = new QuizRequest(null, session, null, null); 
        quizRequest.setQuizRequestContent("Create a quiz containing " + numberOfQuestions + " questions");

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

                quizRequest.setQuizItems(new LinkedHashSet<QuizItem>(Set.of(question1, question2, question3)));
            }

            return quizRequest;
        });

        Mockito.when(quizRequestService.sendQuizRequest(Mockito.any())).thenReturn(quizRequest);
 
    }
    
    @Then("the LLM should ask me to clarify the topic of the quiz")
    public void the_LLM_should_ask_me_to_clarify_the_topic (){
        List<QuizItem> items = new ArrayList<QuizItem>(quizRequest.getQuizItems());
        assertEquals(1,items.size() );
        assertEquals("What should the Quiz be about", items.get(0).getQuestion());
    }
}
