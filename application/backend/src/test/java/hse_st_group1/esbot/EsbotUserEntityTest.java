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

class EsbotUserEntityTest{

    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void testAllArgsConstructorUser(){
        UUID id = UUID.randomUUID();
        String name = "Max";
        Session session = UnitTestHelper.sessionCreator();

        User user = new User(id,name,Set.of(session));
        assertEquals(id, user.getUserID());
        assertEquals(name, user.getUserName());
        assertEquals(session, user.getSessions().iterator().next());
    }

    @Test
    void testSettersUser(){
        UUID dummyId = UUID.randomUUID();
        String dummyName = "Dummy";
        Session dummySession = UnitTestHelper.sessionCreator();
        User user = new User(dummyId, dummyName, Set.of(dummySession));

        UUID id = UUID.randomUUID();
        String name = "Max";
        Session session = UnitTestHelper.sessionCreator();
        session.setLastAccessed(new Timestamp(System.currentTimeMillis()));

        user.setUserID(id);
        user.setUserName(name);
        user.setSessions(Set.of(session));

        assertEquals(id, user.getUserID());
        assertEquals(name, user.getUserName());
        assertEquals(session, user.getSessions().iterator().next());
    }

    @Test
    void testIdConstraintUser(){
        String dummyName = "Dummy";
        Set<Session> dummySession = Set.of(UnitTestHelper.sessionCreator());
        User user = new User(null, dummyName, dummySession);

        Set<ConstraintViolation<User>> uidIsNullViolation = validator.validate(user);
        assertFalse(uidIsNullViolation.isEmpty());
    }

    @Test
    void testNameConstraintUser(){
        UUID dummyId = UUID.randomUUID();
        Set<Session> dummySession = Set.of(UnitTestHelper.sessionCreator());

        User user = new User(dummyId, null, dummySession);
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
        UUID dummyId = UUID.randomUUID();
        String dummyName = "Dummy";

        User user = new User(dummyId, dummyName, null);
        Set<ConstraintViolation<User>> sessionviolation = validator.validate(user);
        assertTrue(sessionviolation.isEmpty());
    }

    @Test
    void testRelationshipsUser(){
        User user = UnitTestHelper.userCreator();
        UnitTestHelper.sessionCreatorWithUser(user);
        Session session = user.getSessions().iterator().next();
        assertEquals(user.getUserName(), session.getUser().getUserName());
        assertEquals(user.getUserID(), session.getUser().getUserID());
        assertEquals(user.getSessions().iterator().next(), session);
    }
}


