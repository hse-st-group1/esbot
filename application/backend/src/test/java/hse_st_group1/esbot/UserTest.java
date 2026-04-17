package hse_st_group1.esbot;

import org.junit.jupiter.api.Test;

import hse_st_group1.esbot.model.Session;
import hse_st_group1.esbot.model.User;
import hse_st_group1.esbot.util.UnitTestHelper;

import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

import static org.junit.jupiter.api.Assertions.*;

class UserTest{

    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    private UUID id = UUID.randomUUID();
    private String name = "Max";
    private Session session = UnitTestHelper.sessionCreator();

    @Test
    void testAllArgsConstructorUser(){
        User user = new User(id,name,Set.of(session));

        assertEquals(id, user.getUserID());
        assertEquals(name, user.getUserName());
        assertEquals(session, user.getSessions().iterator().next());
    }

    @Test
    void testSettersUser(){
        User user = new User(id, name, Set.of(session));
        session.setLastAccessed(new Timestamp(System.currentTimeMillis()));

        UUID newId = UUID.randomUUID();
        String newName = "Dummy";
        Session newSession = UnitTestHelper.sessionCreator();

        user.setUserID(newId);
        user.setUserName(newName);
        user.setSessions(Set.of(newSession));

        assertEquals(newId, user.getUserID());
        assertEquals(newName, user.getUserName());
        assertEquals(newSession, user.getSessions().iterator().next());
    }

    @Test
    void testIdConstraintUser(){
        User user = new User(null, name, Set.of(session));

        Set<ConstraintViolation<User>> uidIsNullViolation = validator.validate(user);
        assertFalse(uidIsNullViolation.isEmpty());
    }

    @Test
    void testNameConstraintUser(){
        User user = new User(id, null, Set.of(session));
        Set<ConstraintViolation<User>> nameIsNullViolation = validator.validate(user);
        assertFalse(nameIsNullViolation.isEmpty());

        user.setUserName("");
        Set<ConstraintViolation<User>> nameIsBlankViolation = validator.validate(user);
        assertFalse(nameIsBlankViolation.isEmpty());

        user.setUserName("A");
        Set<ConstraintViolation<User>> nameIsTooShortViolation = validator.validate(user);
        assertFalse(nameIsTooShortViolation.isEmpty());
        
    }

    @Test
    void testSessionConstraintUser(){
        User user = new User(id, name, null);
        Set<ConstraintViolation<User>> sessionviolation = validator.validate(user);
        assertTrue(sessionviolation.isEmpty());
    }

    @Test
    void testRelationshipsUser(){
        User user = UnitTestHelper.userCreator();
        UnitTestHelper.sessionCreator(user);
        Session userSession = user.getSessions().iterator().next();
        assertEquals(user.getUserName(), userSession.getUser().getUserName());
        assertEquals(user.getUserID(), userSession.getUser().getUserID());
        assertEquals(user.getSessions().iterator().next(), userSession);
    }
}


