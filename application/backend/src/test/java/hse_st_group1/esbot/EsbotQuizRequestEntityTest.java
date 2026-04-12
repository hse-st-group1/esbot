package hse_st_group1.esbot;

import org.junit.jupiter.api.Test;

import hse_st_group1.esbot.model.Session;
import hse_st_group1.esbot.util.UnitTestHelper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.Validation;
import hse_st_group1.esbot.model.QuizItem;
import hse_st_group1.esbot.model.QuizRequest;

import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class EsbotQuizRequestEntityTest{

    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    private UUID id = UUID.randomUUID();
    private String content = "Question";
    private Session session = UnitTestHelper.sessionCreator();

    @Test
    void testAllArgsConstructorQuizRequest(){
        QuizRequest quiz = new QuizRequest(id, session, content, null);

        assertEquals(id, quiz.getQuizID());
        assertEquals(content, quiz.getQuizRequestContent());
        assertEquals(session, quiz.getSession());
    }

    @Test
    void testSettersQuizRequest(){

        QuizRequest quiz = new QuizRequest(id, session, content, null);

        UUID newId = UUID.randomUUID();
        Session newSession = UnitTestHelper.sessionCreator();
        String newContent = "Question 1:";

        quiz.setQuizID(newId);
        quiz.setQuizRequestContent(newContent);
        quiz.setSession(newSession);

        assertEquals(newId, quiz.getQuizID());
        assertEquals(newContent, quiz.getQuizRequestContent());
        assertEquals(newSession, quiz.getSession());
    }

    @Test
    void testIdConstraintQuizRequest(){
        QuizRequest quiz = new QuizRequest(null, session, content, null);
        Set<ConstraintViolation<QuizRequest>> quizIdIsNullViolation = validator.validate(quiz);
        assertFalse(quizIdIsNullViolation.isEmpty());
    }

    @Test
    void testSessionConstraintQuizRequest(){
        QuizRequest quiz = new QuizRequest(id, null, content, null);
        Set<ConstraintViolation<QuizRequest>> quizSessionIsNullViolation = validator.validate(quiz);
        assertFalse(quizSessionIsNullViolation.isEmpty());
    }

    @Test
    void testContentConstraintQuizRequest(){

        QuizRequest quiz = new QuizRequest(id, session, null, null);
        Set<ConstraintViolation<QuizRequest>> quizContentIsNullViolation = validator.validate(quiz);
        assertFalse(quizContentIsNullViolation.isEmpty());

        quiz.setQuizRequestContent("");
        Set<ConstraintViolation<QuizRequest>> quizContentIsBlankViolation = validator.validate(quiz);
        assertFalse(quizContentIsBlankViolation.isEmpty());
    }

    @Test
    void testRelationshipsQuizRequest(){
        QuizRequest quizRequest = UnitTestHelper.quizRequestCreator();
        QuizItem quizItem = UnitTestHelper.quizItemCreatorWithQuizRequest(quizRequest);
        assertEquals(quizRequest.getQuizID(), quizItem.getQuizRequest().getQuizID());
        assertEquals(quizRequest.getSession(), quizItem.getQuizRequest().getSession());
        assertEquals(quizRequest.getQuizRequestContent(), quizItem.getQuizRequest().getQuizRequestContent());
        assertEquals(quizRequest.getQuizItems().iterator().next(), quizItem);
    }
}