package hse_st_group1.esbot;

import org.junit.jupiter.api.Test;

import hse_st_group1.esbot.model.Session;
import hse_st_group1.esbot.model.User;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.Validation;
import hse_st_group1.esbot.model.QuizRequest;

import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class EsbotQuizRequestEntityTest{

    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    //Helper Methods
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

    @Test
    void testAllArgsConstructorQuizRequest(){
        UUID id = UUID.randomUUID();
        String question = "Question 1";

        
        Session session = sessionCreator();

        QuizRequest quiz = new QuizRequest(id, session, question);

        assertEquals(id, quiz.getQuizID());
        assertEquals(question, quiz.getQuizRequestContent());
        assertEquals(session, quiz.getSession());
    }

    @Test
    void testSettersQuizRequest(){
        UUID dummyId = UUID.randomUUID();
        Session dummySession = sessionCreator();
        String dummyContent = "Dummy";

        QuizRequest quiz = new QuizRequest(dummyId, dummySession, dummyContent);

        UUID id = UUID.randomUUID();
        Session session = sessionCreator();
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
        Session dummySession = sessionCreator();
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
        Session dummySession = sessionCreator();

        QuizRequest quiz = new QuizRequest(dummyId, dummySession, null);
        Set<ConstraintViolation<QuizRequest>> quizContentIsNullViolation = validator.validate(quiz);
        assertFalse(quizContentIsNullViolation.isEmpty());

        quiz.setQuizRequestContent("");
        Set<ConstraintViolation<QuizRequest>> quizContentIsBlankViolation = validator.validate(quiz);
        assertFalse(quizContentIsBlankViolation.isEmpty());
    }

    @Test
    void testSessionIdQuizRequest(){
        UUID id = UUID.randomUUID();
        String question = "Question 1";
        Session session = sessionCreator();
        QuizRequest quiz = new QuizRequest(id, session, question);
        assertEquals(session.getSessionID(), quiz.getSession().getSessionID());
    }
}