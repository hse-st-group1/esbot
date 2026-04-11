package hse_st_group1.esbot;

import org.junit.jupiter.api.Test;

import hse_st_group1.esbot.model.QuizAnswer;
import hse_st_group1.esbot.model.QuizEvaluation;
import hse_st_group1.esbot.model.QuizItem;
import hse_st_group1.esbot.model.QuizRequest;
import hse_st_group1.esbot.model.Session;
import hse_st_group1.esbot.model.User;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class EsbotEvaluationEntityTest {

    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    //Helper Methods
    QuizItem quizItemCreator(){
        UUID quizItemId = UUID.randomUUID();
        QuizRequest quizRequest = quizRequestCreator();
        String question = "Question";
        QuizItem quizItem = new QuizItem(quizItemId, quizRequest, question, null, null);
        quizItem.setQuizAnswers(Set.of(quizAnswerCreator(quizItem)));
        return quizItem;
    }
    Session sessionCreator(){
        UUID sessionId = UUID.randomUUID();
        User user = userCreator();
        Timestamp startedAt = new Timestamp(System.currentTimeMillis());
        Timestamp lastAccessed = new Timestamp(System.currentTimeMillis());
        Session session = new Session(sessionId, user, startedAt, lastAccessed, null, null);
        user.setSessions(Set.of(session));
        return session;
    }
    User userCreator(){
        UUID userId = UUID.randomUUID();
        String userName = "Max";
        return new User(userId, userName, null);
    }
    QuizRequest quizRequestCreator(){
        UUID quizRequestId = UUID.randomUUID();
        Session session = sessionCreator();
        String content = "Test";
        return new QuizRequest(quizRequestId, session, content);
    }
    QuizAnswer quizAnswerCreator(QuizItem item){
        UUID quizAnswerId = UUID.randomUUID();
        QuizItem quizItem = item;
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return new QuizAnswer(quizAnswerId, quizItem, "", timestamp);
    }

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
    
    @Test
    void testQuizItemIdQuizEvaluation(){
        UUID evaluationID = UUID.randomUUID();
        QuizItem quizItem = quizItemCreator();
        QuizAnswer quizAnswer = quizItem.getQuizAnswers().iterator().next();
        String evaluation = "OK";
        QuizEvaluation quizEvaluation = new QuizEvaluation(evaluationID, quizItem, quizAnswer, evaluation);
        assertEquals(quizItem.getQuizItemID(), quizEvaluation.getQuizItem().getQuizItemID());
    }

    @Test
    void testQuizAnswerQuizEvaluation(){
        UUID evaluationID = UUID.randomUUID();
        QuizItem quizItem = quizItemCreator();
        QuizAnswer quizAnswer = quizItem.getQuizAnswers().iterator().next();
        String evaluation = "OK";
        QuizEvaluation quizEvaluation = new QuizEvaluation(evaluationID, quizItem, quizAnswer, evaluation);
        assertEquals(quizAnswer.getAnswer(), quizEvaluation.getQuizAnswer().getAnswer());
    }
}
