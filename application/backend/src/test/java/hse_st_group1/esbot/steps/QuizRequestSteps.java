package hse_st_group1.esbot.steps;
// GPT 5.4 was used for debbugging and improving understanding of error messages
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import hse_st_group1.esbot.AIServiceUnavailableException;
import hse_st_group1.esbot.model.QuizItem;
import hse_st_group1.esbot.model.QuizRequest;
import hse_st_group1.esbot.repository.QuizAnswerRepository;
import hse_st_group1.esbot.repository.QuizEvaluationRepository;
import hse_st_group1.esbot.repository.QuizItemRepository;
import hse_st_group1.esbot.repository.QuizRequestRepository;
import hse_st_group1.esbot.repository.SessionRepository;
import hse_st_group1.esbot.repository.UserRepository;
import hse_st_group1.esbot.services.AIService;
import hse_st_group1.esbot.services.QuizRequestService;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.But;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import jakarta.transaction.Transactional;

public class QuizRequestSteps {

    private QuizRequest quizRequest;
    private QuizRequest newQuizRequest;

    private RuntimeException exception;

    private int numberOfQuestions;
    private String exampleQuizTopic;

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
    @Autowired
    private QuizEvaluationRepository quizEvaluationRepository;
    @Autowired 
    private QuizAnswerRepository quizAnswerRepository;
    @Autowired
    private AIService aiService;
    @Autowired
    private SharedSession session;

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
        Mockito.when(aiService.isAvailable()).thenReturn(true);
        Mockito.when(aiService.createQuestions(Mockito.anyString())).thenReturn(List.of("Content of Question 1", "Content of Question 2", "Content of Question 3"));

        quizRequest = new QuizRequest();
        quizRequest.setSession(session.getSession()); 
        quizRequest.setQuizRequestContent("Create a quiz about " + exampleQuizTopic + " containing " + numberOfQuestions + " questions");

        try{
            newQuizRequest = quizRequestService.createQuiz(quizRequest);
        }
        catch(AIServiceUnavailableException serviceUnavailableException){
            exception = serviceUnavailableException;
        }
    }

    @Then("a list of questions should be shown to me")
    public void a_list_of_questions_should_be_shown_to_me(){
        assertNotNull(newQuizRequest);
        List<QuizItem> items = new ArrayList<QuizItem>(newQuizRequest.getQuizItems());
        assertEquals("Content of Question 1", items.get(0).getQuestion());
        assertEquals("Content of Question 2", items.get(1).getQuestion());
        assertEquals("Content of Question 3", items.get(2).getQuestion());
    }

    @When("I request a quiz")
    public void i_request_a_quiz(){
        Mockito.when(aiService.isAvailable()).thenReturn(true);
        Mockito.when(aiService.createQuestions(Mockito.anyString())).thenReturn(List.of("Content of Question 1", "Content of Question 2", "Content of Question 3"));

        quizRequest = new QuizRequest();
        quizRequest.setSession(session.getSession()); 
        quizRequest.setQuizRequestContent("Create a quiz containing " + numberOfQuestions + " questions");
    }

    @But("do not provide the topic of the quiz or exercise")
    public void i_request_a_quiz_or_an_exercise_without_providing_a_topic(){
        try{
            quizRequestService.createQuiz(quizRequest);
        }
        catch(AIServiceUnavailableException serviceUnavailableException){
            exception = serviceUnavailableException;
        }
        
 
    }
    
    @Then("the LLM should inform me that I need to clarify the topic of the quiz")
    public void the_LLM_should_inform_me_that_I_need_to_clarify_the_topic (){
        assertEquals("Error: No quiz topic provided.", exception.getMessage());
    }

    @After
    @Transactional
    public void cleanDB() {
        quizEvaluationRepository.deleteAll();
        quizAnswerRepository.deleteAll();
        quizItemRepository.deleteAll();
        quizRequestRepository.deleteAll();
        sessionRepository.deleteAll();
        userRepository.deleteAll();
    }
}
