package hse_st_group1.esbot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import hse_st_group1.esbot.model.*;
import hse_st_group1.esbot.util.UnitTestHelper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

class QuizItemTest {

    // Validator checks NotNull Constraints that can otherwise not be tested without Database Connection
    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    
    // Used Arguments for Tests
    UUID sessionID = UUID.randomUUID();
    User user = UnitTestHelper.userCreator();

    UUID quizItemID = UUID.randomUUID();
    QuizRequest quizRequestWithSessionID = UnitTestHelper.quizRequestCreatorWithSession(UnitTestHelper.sessionCreator(sessionID));
    QuizRequest quizRequestWithoutSessionID = UnitTestHelper. quizRequestCreator();
    String question = "Question Content";
    Set<QuizAnswer> quizAnswers = Set.of(UnitTestHelper.quizAnswerCreator(UnitTestHelper.quizItemCreator(quizItemID)));
    Set<QuizEvaluation> quizEvaluations = Set.of(UnitTestHelper.quizEvaluationCreator(UnitTestHelper.quizItemCreator(quizItemID)));

    @Test
    void testAllArgsConstructorQuizItem () {
        // Create Test Object
        QuizItem quizItem = new QuizItem(quizItemID, quizRequestWithSessionID, question, quizAnswers, quizEvaluations);
        
        // Assert that Object was created sucessfully
        assertThat(quizItem).isNotNull();
        
        //Assert that Parameters are correctly mapped to Properties
        assertThat(quizItem.getQuizItemID()).isEqualTo(quizItemID);
        assertThat(quizItem.getQuizRequest()).isEqualTo(quizRequestWithSessionID);
        assertThat(quizItem.getQuestion()).isEqualTo(question);
        assertThat(quizItem.getQuizAnswers()).isEqualTo(quizAnswers);
        assertThat(quizItem.getQuizEvaluations()).isEqualTo(quizEvaluations);
    }

    @Test
    void testNotNullConstraintsQuizItem () {
        // Create Test Object with no arguments (default = null)
        QuizItem quizItem = new QuizItem();
        
        // Assert that Object was created sucessfully
        assertThat(quizItem).isNotNull();
       
        //Assert that NotNull constrainst are implemented 
        Set<ConstraintViolation<QuizItem>> quizItemIDNotNullValidation = validator.validateProperty(quizItem, "quizItemID");
        assertThat(quizItemIDNotNullValidation).isNotNull();

        Set<ConstraintViolation<QuizItem>> quizRequestNotNullValidation = validator.validateProperty(quizItem, "quizRequest");
        assertThat(quizRequestNotNullValidation).isNotNull();

        Set<ConstraintViolation<QuizItem>> questionNotEmptyValidation = validator.validateProperty(quizItem, "question");
        assertThat(questionNotEmptyValidation).isNotEmpty();

        // Assert that nullable Properties do not throw Errors
        Set<ConstraintViolation<QuizItem>> quizAnswerEmptyValidation = validator.validateProperty(quizItem, "quizAnswers");
        assertThat(quizAnswerEmptyValidation).isEmpty();

        Set<ConstraintViolation<QuizItem>> quizEvaluationEmptyValidation = validator.validateProperty(quizItem, "quizEvaluations");
        assertThat(quizEvaluationEmptyValidation).isEmpty();
    }

    @Test
    void testGettersAndSettersQuizItem () {
        // Create Test Object
        QuizItem quizItem = new QuizItem();
        
        // Assert that Object was created sucessfully
        assertThat(quizItem).isNotNull();
        
        // Set Properties (question, quizAnswers and quizEvaluations should be able to be updated/set)
        quizItem.setQuestion(question);
        assertThat(quizItem.getQuestion()).isEqualTo(question);

        quizItem.setQuizAnswers(quizAnswers);
        assertThat(quizItem.getQuizAnswers()).isEqualTo(quizAnswers);

        quizItem.setQuizEvaluations(quizEvaluations);
        assertThat(quizItem.getQuizEvaluations()).isEqualTo(quizEvaluations);
    }

    @Test
    void testUpdateConstraintsQuizItem () {
        // quizItemID and quizRequest should not be able to be updated

        // Create Test Object
        QuizItem quizItem = new QuizItem(quizItemID, quizRequestWithSessionID, question, quizAnswers, quizEvaluations);
        
        // Assert that Object was created sucessfully
        assertThat(quizItem).isNotNull();
        
        // Set Properties (quizItemID and quizRequest should not be able to be set)
        UUID newquizItemID = UUID.randomUUID(); 
        assertThrows(UnsupportedOperationException.class, () -> quizItem.setQuizItemID(newquizItemID));
        
        QuizRequest newQuizRequest = UnitTestHelper.quizRequestCreator();
        assertThrows(UnsupportedOperationException.class, () -> quizItem.setQuizRequest(newQuizRequest));
    }

    @Test
    void testRelationshipsQuizItem () {
        // QuizItem is connected to QuizRequest, QuizAnswers, QuizEvaluations
        QuizItem quizItem = new QuizItem(quizItemID, quizRequestWithSessionID, question, quizAnswers, quizEvaluations);
        assertThat(quizItem.getQuizRequest()).isEqualTo(quizRequestWithSessionID);
        
        QuizAnswer newQuizAnswer = UnitTestHelper.quizAnswerCreator(quizItem);
        Set<QuizAnswer> newQuizAnswers = Set.of(newQuizAnswer);
        quizItem.setQuizAnswers(newQuizAnswers);
        assertThat(quizItem.getQuizAnswers()).isEqualTo(newQuizAnswers);

        QuizEvaluation newQuizEvaluation = UnitTestHelper.quizEvaluationCreator(quizItem);
        Set<QuizEvaluation> newQuizEvaluations = Set.of(newQuizEvaluation);
        quizItem.setQuizEvaluations(newQuizEvaluations);
        assertThat(quizItem.getQuizEvaluations()).isEqualTo(newQuizEvaluations);
        assertThat(quizItem.getQuizEvaluations()).contains(newQuizEvaluation);
    }
}