package hse_st_group1.esbot;

import org.junit.jupiter.api.Test;

import hse_st_group1.esbot.model.QuizAnswer;
import hse_st_group1.esbot.model.QuizEvaluation;
import hse_st_group1.esbot.model.QuizItem;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class EsbotEvaluationEntityTest {

    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void testAllArgsConstructorQuizEvaluation(){
        UUID evaluationID = UUID.randomUUID();
        QuizItem quizItem = new QuizItem(null,null,null,null,null);
        QuizAnswer quizAnswer = new QuizAnswer();
        String evaluation = "Correct!";

        QuizEvaluation quizEvaluation = new QuizEvaluation(evaluationID, quizItem, quizAnswer, evaluation);

        assertEquals(evaluation, quizEvaluation.getEvaluation());
        assertEquals(evaluationID, quizEvaluation.getEvaluationID());
        assertEquals(quizItem, quizEvaluation.getQuizItem());
        assertEquals(quizAnswer, quizEvaluation.getQuizAnswer());
    }

    @Test
    void testSettersQuizEvaluation(){
        UUID dummyId = UUID.randomUUID();
        QuizItem dummyItem = new QuizItem(dummyId, null, null, null, null);
        QuizAnswer dummyAnswer = new QuizAnswer();
        String dummyEvaluation = "Dummy";

        QuizEvaluation quizEvaluation = new QuizEvaluation(dummyId, dummyItem, dummyAnswer, dummyEvaluation);

        UUID evaluationID = UUID.randomUUID();
        QuizItem quizItem = new QuizItem(null,null,null,null,null);
        QuizAnswer quizAnswer = new QuizAnswer();
        String evaluation = "Correct!";

        quizEvaluation.setEvaluationID(evaluationID);
        quizEvaluation.setQuizItem(quizItem);
        quizEvaluation.setQuizAnswer(quizAnswer);
        quizEvaluation.setEvaluation(evaluation);

        assertEquals(evaluation, quizEvaluation.getEvaluation());
        assertEquals(evaluationID, quizEvaluation.getEvaluationID());
        assertEquals(quizItem, quizEvaluation.getQuizItem());
        assertEquals(quizAnswer, quizEvaluation.getQuizAnswer());
    }

    @Test
    void testIdConstraintQuizEvaluation(){
        UUID dummyQuizItemId = UUID.randomUUID();
        QuizItem dummyItem = new QuizItem(dummyQuizItemId, null, null, null, null);
        QuizAnswer dummyAnswer = new QuizAnswer();
        String dummyEvaluation = "Dummy";

        QuizEvaluation quizEvaluation = new QuizEvaluation(null, dummyItem, dummyAnswer, dummyEvaluation);
        Set<ConstraintViolation<QuizEvaluation>> quizIdIsNullConstraintViolation = validator.validate(quizEvaluation);
        assertFalse(quizIdIsNullConstraintViolation.isEmpty());
    }

    @Test
    void testQuizItemConstraintQuizEvaluation(){
        UUID dummyId = UUID.randomUUID();
        QuizAnswer dummyAnswer = new QuizAnswer();
        String dummyEvaluation = "Dummy";

        QuizEvaluation quizEvaluation = new QuizEvaluation(dummyId, null, dummyAnswer, dummyEvaluation);
        Set<ConstraintViolation<QuizEvaluation>> quizItemIsNullConstraintViolation = validator.validate(quizEvaluation);
        assertFalse(quizItemIsNullConstraintViolation.isEmpty());
    }

    @Test
    void testQuizAnswerConstraintQuizEvaluation(){
        UUID dummyQuizItemId = UUID.randomUUID();
        QuizItem dummyItem = new QuizItem(dummyQuizItemId, null, null, null, null);
        String dummyEvaluation = "Dummy";

        QuizEvaluation quizEvaluation = new QuizEvaluation(dummyQuizItemId, dummyItem, null, dummyEvaluation);
        Set<ConstraintViolation<QuizEvaluation>> quizAnswerIsNullConstraintViolation = validator.validate(quizEvaluation);
        assertFalse(quizAnswerIsNullConstraintViolation.isEmpty());
    }

    @Test
    void testEvaluationConstraintQuizEvaluation(){
        UUID dummyQuizItemId = UUID.randomUUID();
        QuizItem dummyItem = new QuizItem(dummyQuizItemId, null, null, null, null);
        QuizAnswer dummyAnswer = new QuizAnswer();
        
        QuizEvaluation quizEvaluation = new QuizEvaluation(dummyQuizItemId, dummyItem, dummyAnswer, null);
        Set<ConstraintViolation<QuizEvaluation>> quizEvaluationIsNullConstraintViolation = validator.validate(quizEvaluation);
        assertFalse(quizEvaluationIsNullConstraintViolation.isEmpty());

        quizEvaluation.setEvaluation("");
        Set<ConstraintViolation<QuizEvaluation>> quizEvaluationIsBlankConstraintViolation = validator.validate(quizEvaluation);
        assertFalse(quizEvaluationIsBlankConstraintViolation.isEmpty());
    }
    
}
