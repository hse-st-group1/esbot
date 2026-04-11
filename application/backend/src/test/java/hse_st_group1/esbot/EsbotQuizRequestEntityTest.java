package hse_st_group1.esbot;

import org.junit.jupiter.api.Test;

import hse_st_group1.esbot.model.Session;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.Validation;
import hse_st_group1.esbot.model.QuizRequest;

import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class EsbotQuizRequestEntityTest{

    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void testAllArgsConstructorQuizRequest(){
        UUID id = UUID.randomUUID();
        String question = "Question 1";
        Session session = new Session();

        QuizRequest quiz = new QuizRequest(id, session, question);

        assertEquals(id, quiz.getQuizID());
        assertEquals(question, quiz.getQuizRequestContent());
        assertEquals(session, quiz.getSession());
    }

    @Test
    void testSettersQuizRequest(){
        UUID dummyId = UUID.randomUUID();
        Session dummySession = new Session();
        String dummyContent = "Dummy";

        QuizRequest quiz = new QuizRequest(dummyId, dummySession, dummyContent);

        UUID id = UUID.randomUUID();
        Session session = new Session();
        String question = "Question 1:";

        quiz.setQuizID(id);
        quiz.setQuizRequestContent(question);
        quiz.setSession(session);

        assertEquals(id, quiz.getQuizID());
        assertEquals(question, quiz.getQuizRequestContent());
        assertEquals(session, quiz.getSession());
    }

    @Test
    void testIdConstraintQuizRequest(){
        Session dummySession = new Session();
        String dummyContent = "Dummy";

        QuizRequest quiz = new QuizRequest(null, dummySession, dummyContent);
        Set<ConstraintViolation<QuizRequest>> quizIdIsNullViolation = validator.validate(quiz);
        assertFalse(quizIdIsNullViolation.isEmpty());
    }

    @Test
    void testSessionConstraintQuizRequest(){
        UUID dummyId = UUID.randomUUID();
        String dummyContent = "Dummy";

        QuizRequest quiz = new QuizRequest(dummyId, null, dummyContent);
        Set<ConstraintViolation<QuizRequest>> quizSessionIsNullViolation = validator.validate(quiz);
        assertFalse(quizSessionIsNullViolation.isEmpty());
    }

    @Test
    void testContentConstraintQuizRequest(){
        UUID dummyId = UUID.randomUUID();
        Session dummySession = new Session();

        QuizRequest quiz = new QuizRequest(dummyId, dummySession, null);
        Set<ConstraintViolation<QuizRequest>> quizContentIsNullViolation = validator.validate(quiz);
        assertFalse(quizContentIsNullViolation.isEmpty());

        quiz.setQuizRequestContent("");
        Set<ConstraintViolation<QuizRequest>> quizContentIsBlankViolation = validator.validate(quiz);
        assertFalse(quizContentIsBlankViolation.isEmpty());
    }
}