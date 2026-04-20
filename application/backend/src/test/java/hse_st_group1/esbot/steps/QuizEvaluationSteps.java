package hse_st_group1.esbot.steps;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import hse_st_group1.esbot.model.QuizAnswer;
import hse_st_group1.esbot.model.QuizEvaluation;
import hse_st_group1.esbot.model.QuizItem;
import hse_st_group1.esbot.model.QuizRequest;
import hse_st_group1.esbot.repository.QuizAnswerRepository;
import hse_st_group1.esbot.repository.QuizEvaluationRepository;
import hse_st_group1.esbot.repository.QuizItemRepository;
import hse_st_group1.esbot.repository.QuizRequestRepository;
import hse_st_group1.esbot.repository.SessionRepository;
import hse_st_group1.esbot.repository.UserRepository;
import hse_st_group1.esbot.services.AIService;
import hse_st_group1.esbot.services.QuizEvaluationService;
import hse_st_group1.esbot.services.QuizRequestService;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import jakarta.transaction.Transactional;

public class QuizEvaluationSteps {

    @Autowired
    UserRepository userRepository;

    @Autowired
    SessionRepository sessionRepository;

    @Autowired 
    QuizItemRepository quizItemRepository;

    @Autowired
    QuizRequestRepository quizRequestRepository;

    @Autowired 
    QuizRequestService quizRequestService;

    @Autowired
    QuizEvaluationService quizEvaluationService;

    @Autowired
    QuizAnswerRepository quizAnswerRepository;

    @Autowired
    QuizEvaluationRepository quizEvaluationRepository;

    @Autowired
    SharedSession sharedSession;

    private QuizRequest quizRequest;
    private QuizItem quizItem;
    private QuizAnswer answer;
    private QuizEvaluation evaluation;

    @Autowired
    AIService aiService;

    @Before
    public void resetMocks() {
        Mockito.reset(aiService);
    }

    @Given("I have a question")
    public void i_have_a_question() {
        String question = "How can I test software, to ensure it has no bugs?";
        quizRequest = new QuizRequest();
        quizRequest.setQuizRequestContent("Topic: Testquiz");
        quizRequest.setSession(sharedSession.getSession());
        quizItem = new QuizItem(null, quizRequest, question, null, null);
        List<QuizItem> questions = new ArrayList<>();
        questions.add(quizItem);
        quizRequest.setQuizItems(questions);
        quizRequestRepository.save(quizRequest);
        quizItemRepository.save(quizItem);
    }
    
    @When("I give an as correct interpretable answer")
    public void i_give_correct_answer() {
        answer = new QuizAnswer(
            null, 
            quizItem, 
            "42", 
            new Timestamp(System.currentTimeMillis())
        );
        quizAnswerRepository.save(answer);
        Mockito.when(aiService.evaluateAnswer("42")).thenReturn("correct");
        evaluation = quizEvaluationService.evaluate(answer);
    }

    @When("I give an as incorrect interpretable answer")
    public void i_give_incorrect_answer() {
        answer = new QuizAnswer(
            null, 
            quizItem, 
            "Rabbit", 
            new Timestamp(System.currentTimeMillis())
        );
        quizAnswerRepository.save(answer);
        Mockito.when(aiService.evaluateAnswer("Rabbit")).thenReturn("Incorrect, pls hava a look in ...");
        evaluation = quizEvaluationService.evaluate(answer);
    }

    @Then("the system evaluates my answer as incorrect")
    public void answer_incorrect() {
        assertEquals("Incorrect, pls hava a look in ...", evaluation.getEvaluation());
    }


    @Then("the system evaluates my answer as correct")
    public void answer_correct() {
        assertEquals("correct", evaluation.getEvaluation());
    }

    @When("I give an invalid string as answer")
    public void i_give_an_invalid_string_as_answer() {
        answer = new QuizAnswer(
            null, 
            quizItem, 
            "dgjasdflöasjfdk", 
            new Timestamp(System.currentTimeMillis())
        );
        quizAnswerRepository.save(answer);
        Mockito.when(aiService.evaluateAnswer("dgjasdflöasjfdk")).thenReturn("Please make a valid and interpretable input");
        evaluation = quizEvaluationService.evaluate(answer);
    }
    @Then("the system asks me to make a valid input.")
    public void the_system_asks_me_to_make_a_valid_input() {
        assertEquals("Please make a valid and interpretable input", evaluation.getEvaluation());
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
