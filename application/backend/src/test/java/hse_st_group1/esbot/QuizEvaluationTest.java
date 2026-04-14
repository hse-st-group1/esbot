package hse_st_group1.esbot;

import org.junit.jupiter.api.Test;

import hse_st_group1.esbot.model.QuizAnswer;
import hse_st_group1.esbot.model.QuizEvaluation;
import hse_st_group1.esbot.model.QuizItem;
import hse_st_group1.esbot.util.UnitTestHelper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class QuizEvaluationTest {

    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    private UUID evaluationID = UUID.randomUUID();
    private QuizItem quizItem = UnitTestHelper.quizItemCreator();
    private QuizAnswer quizAnswer = UnitTestHelper.quizAnswerCreator(quizItem);
    private String evaluation = "Correct!";

    @Test
    void testAllArgsConstructorQuizEvaluation(){

        QuizEvaluation quizEvaluation = new QuizEvaluation(evaluationID, quizItem, quizAnswer, evaluation);

        assertEquals(evaluation, quizEvaluation.getEvaluation());
        assertEquals(evaluationID, quizEvaluation.getEvaluationID());
        assertEquals(quizItem, quizEvaluation.getQuizItem());
        assertEquals(quizAnswer, quizEvaluation.getQuizAnswer());
    }

    @Test
    void testSettersQuizEvaluation(){
        QuizEvaluation quizEvaluation = UnitTestHelper.quizEvaluationCreator();

        UUID newEvaluationID = UUID.randomUUID();
        QuizItem newQuizItem = UnitTestHelper.quizItemCreator();
        QuizAnswer newQuizAnswer = UnitTestHelper.quizAnswerCreator(quizItem);
        String newEvaluation = "Not Correct!";

        quizEvaluation.setEvaluationID(newEvaluationID);
        quizEvaluation.setQuizItem(newQuizItem);
        quizEvaluation.setQuizAnswer(newQuizAnswer);
        quizEvaluation.setEvaluation(newEvaluation);

        assertEquals(newEvaluation, quizEvaluation.getEvaluation());
        assertEquals(newEvaluationID, quizEvaluation.getEvaluationID());
        assertEquals(newQuizItem, quizEvaluation.getQuizItem());
        assertEquals(newQuizAnswer, quizEvaluation.getQuizAnswer());
    }

    @Test
    void testIdConstraintQuizEvaluation(){
        QuizEvaluation quizEvaluation = new QuizEvaluation(null, quizItem, quizAnswer, evaluation);
        Set<ConstraintViolation<QuizEvaluation>> quizIdIsNullConstraintViolation = validator.validate(quizEvaluation);
        assertFalse(quizIdIsNullConstraintViolation.isEmpty());
    }

    @Test
    void testQuizItemConstraintQuizEvaluation(){
        QuizEvaluation quizEvaluation = new QuizEvaluation(evaluationID, null, quizAnswer, evaluation);
        Set<ConstraintViolation<QuizEvaluation>> quizItemIsNullConstraintViolation = validator.validate(quizEvaluation);
        assertFalse(quizItemIsNullConstraintViolation.isEmpty());
    }

    @Test
    void testQuizAnswerConstraintQuizEvaluation(){
        QuizEvaluation quizEvaluation = new QuizEvaluation(evaluationID, quizItem, null, evaluation);
        Set<ConstraintViolation<QuizEvaluation>> quizAnswerIsNullConstraintViolation = validator.validate(quizEvaluation);
        assertFalse(quizAnswerIsNullConstraintViolation.isEmpty());
    }

    @Test
    void testEvaluationConstraintQuizEvaluation(){
        QuizEvaluation quizEvaluation = new QuizEvaluation(evaluationID, quizItem, quizAnswer, null);
        Set<ConstraintViolation<QuizEvaluation>> quizEvaluationIsNullConstraintViolation = validator.validate(quizEvaluation);
        assertFalse(quizEvaluationIsNullConstraintViolation.isEmpty());

        quizEvaluation.setEvaluation("");
        Set<ConstraintViolation<QuizEvaluation>> quizEvaluationIsBlankConstraintViolation = validator.validate(quizEvaluation);
        assertFalse(quizEvaluationIsBlankConstraintViolation.isEmpty());
    }

    @Test
    void testRelationshipsQuizEvaluation(){
        QuizEvaluation quizEvaluation = new QuizEvaluation(evaluationID, quizItem, quizAnswer, evaluation);
        quizItem.setQuizEvaluations(Set.of(quizEvaluation));
        quizItem.setQuizAnswers(Set.of(quizAnswer));

        assertEquals(quizAnswer.getAnswer(), quizEvaluation.getQuizAnswer().getAnswer());
        assertEquals(quizItem.getQuizItemID(), quizEvaluation.getQuizItem().getQuizItemID());
        assertEquals(quizEvaluation, quizItem.getQuizEvaluations().iterator().next());
        assertEquals(quizEvaluation.getQuizAnswer(), quizItem.getQuizAnswers().iterator().next());
    }
}
