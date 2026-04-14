package hse_st_group1.esbot;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import hse_st_group1.esbot.model.*;
import hse_st_group1.esbot.util.UnitTestHelper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

class EsbotSessionEntityTest {

    // Validator checks NotNull Constraints that can otherwise not be tested without Database Connection
    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    
    // Used Arguments for Tests
    UUID sessionID = UUID.randomUUID();
    User user = UnitTestHelper.userCreator();
    Timestamp startedAt = new Timestamp(System.currentTimeMillis());
    Timestamp lastAccessed = new Timestamp(System.currentTimeMillis());
    Set<Message> messages = Set.of(UnitTestHelper.createTestMessage(UnitTestHelper.sessionCreator(sessionID)));
    Set<QuizRequest> quizRequests = Set.of(UnitTestHelper.quizRequestCreatorWithSession(UnitTestHelper.sessionCreator(sessionID)));

    @Test
    void testAllArgsConstructorSession () {
        // Create Test Object
        Session session = new Session(sessionID, user, startedAt, lastAccessed, null, null);
        
        // Assert that Object was created sucessfully
        assertThat(session).isNotNull();
        
        //Assert that Parameters are correctly mapped to Properties
        assertThat(session.getSessionID()).isEqualTo(sessionID);
        assertThat(session.getUser()).isEqualTo(user);
        assertThat(session.getStartedAt()).isEqualTo(startedAt);
        assertThat(session.getLastAccessed()).isEqualTo(lastAccessed);
    }

    @Test
    void testNotNullConstraintsSessions () {
        // Create Test Object with no arguments (default = null)
        Session session = new Session();
        
        // Assert that Object was created sucessfully
        assertThat(session).isNotNull();
       
        //Assert that NotNull constrainst are implemented
        Set<ConstraintViolation<Session>> sessionNoArgsConstructorSessionIDValidation = validator.validateProperty(session, "sessionID");
        assertThat(sessionNoArgsConstructorSessionIDValidation).isNotEmpty();

        Set<ConstraintViolation<Session>> sessionNoArgsConstructorUserValidation = validator.validateProperty(session, "user");
        assertThat(sessionNoArgsConstructorUserValidation).isNotEmpty();

        Set<ConstraintViolation<Session>> sessionNoArgsConstructorStartedAtValidation = validator.validateProperty(session, "startedAt");
        assertThat(sessionNoArgsConstructorStartedAtValidation).isNotEmpty();

        Set<ConstraintViolation<Session>> sessionNoArgsConstructorLastAccessedValidation = validator.validateProperty(session, "lastAccessed");
        assertThat(sessionNoArgsConstructorLastAccessedValidation).isNotEmpty();

        // Assert that nullable Properties do not throw Errors
        Set<ConstraintViolation<Session>> sessionNoArgsConstructorMessagesValidation = validator.validateProperty(session, "messages");
        assertThat(sessionNoArgsConstructorMessagesValidation).isEmpty();

        Set<ConstraintViolation<Session>> sessionNoArgsConstructorQuizRequestValidation = validator.validateProperty(session, "messages");
        assertThat(sessionNoArgsConstructorQuizRequestValidation).isEmpty();
        
    }

    @Test
    void testGettersAndSettersSession () {
        // Create Test Object
        Session session = new Session();
        
        // Assert that Object was created sucessfully
        assertThat(session).isNotNull();
        
        // Set Properties (lastAccessed, messegas and quizrequest should be able to be updated/set)
        session.setLastAccessed(lastAccessed);
        assertThat(session.getLastAccessed()).isEqualTo(lastAccessed);
        
        session.setMessages(messages);
        assertThat(session.getMessages()).isEqualTo(messages);
        
        session.setQuizRequests(quizRequests);
        assertThat(session.getQuizRequests()).isEqualTo(quizRequests);
    }

    @Test
    void testUpdateConstraintsSession () {
        // sessionID, user and startedAt should not be able to be updated

        // Create Test Object
        Session session = new Session(sessionID, user, startedAt, lastAccessed, null, null);
        
        // Assert that Object was created sucessfully
        assertThat(session).isNotNull();
        
        // Set Properties (sessionID, user and startedAt should not be able to be set)
        UUID newSessionID = UUID.randomUUID(); 
        assertThrows(UnsupportedOperationException.class, () -> session.setSessionID(newSessionID));
        
        UUID userID = UUID.randomUUID();
        User newUser = new User(userID, "Max Mustermann", null);
        assertThrows(UnsupportedOperationException.class, () -> session.setUser(newUser));

        Timestamp newStartedAt = new Timestamp(0);
        assertThrows(UnsupportedOperationException.class, () -> session.setStartedAt(newStartedAt));
    }

    @Test
    void testRelationshipsSession () {
        // Session is linked to a User
        Session session = UnitTestHelper.sessionCreatorWithUser(user);
        assertThat(session.getUser()).isEqualTo(user);
        
        // Session can have many Messages
        Message message = UnitTestHelper.createTestMessage(sessionID);
        session.setMessages(Set.of(message));
        assertThat(message.getSession().getSessionID()).isEqualTo(sessionID);
        assertThat(session.getMessages()).isEqualTo(Set.of(message));

        // Session can have many QuizRequests
        QuizRequest quizRequest= UnitTestHelper.quizRequestCreatorWithSession(UnitTestHelper.sessionCreator(sessionID));
        session.setQuizRequests(Set.of(quizRequest));
        assertThat(quizRequest.getSession().getSessionID()).isEqualTo(sessionID);
        assertThat(session.getQuizRequests()).isEqualTo(Set.of(quizRequest));
    }
}