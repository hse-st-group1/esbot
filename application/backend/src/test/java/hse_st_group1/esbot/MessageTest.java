package hse_st_group1.esbot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import hse_st_group1.esbot.model.Message;
import hse_st_group1.esbot.model.Session;
import hse_st_group1.esbot.util.UnitTestHelper;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ConstraintViolation;


class MessageTest {

    private static Validator validator;

    @BeforeAll
    static void setupValidatorInstance() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    private String messageContent= "Valid Message!";
    private UUID id = UUID.randomUUID();
    private Session s = UnitTestHelper.sessionCreator();

    @Test
    void testCreateMessage() {

        Timestamp t = new Timestamp(System.currentTimeMillis());
        Message message = new Message(this.id, s, this.messageContent,  t, true, "Question");

        assertEquals(this.id, message.getMessageID()); 
        assertEquals(messageContent, message.getMessageContent()); 
        assertEquals(t, message.getTimestamp()); 
        assertEquals(this.s, message.getSession());
        assertEquals(true, message.getSender());
        assertEquals("Question", message.getMessageType());
    }

    @Test
    void testMessageSetter() {
        Message message = UnitTestHelper.createTestMessage();
        UUID testID = UUID.randomUUID();
        Session testSession = UnitTestHelper.sessionCreator(message.getSession().getUser());
        Timestamp testTimestamp = new Timestamp(System.currentTimeMillis());

        message.setMessageID(testID);
        message.setSession(testSession);
        message.setMessageContent("More valid message!");
        message.setTimestamp(testTimestamp);
        message.setSender(true);
        message.setMessageType("ANSWER");

        assertEquals(testID, message.getMessageID());
        assertEquals(testSession, message.getSession());
        assertEquals("More valid message!", message.getMessageContent());
        assertEquals(testTimestamp, message.getTimestamp());
        assertEquals(true, message.getSender());
        assertEquals("ANSWER", message.getMessageType());
    }

    @Test 
    void testSessionNotNullConstraint () {
        Message message = UnitTestHelper.createTestMessage();
        message.setSession(null);

        testSingleConstraint(message, "must not be null", "session");
    }

    @Test 
    void testMessageContentNotBlankConstraint () {
        Message message = UnitTestHelper.createTestMessage();
        message.setMessageContent(" ");

        testSingleConstraint(message, "must not be blank", "messageContent");
    }

    @Test 
    void testTimestampNotNullConstraint () {
        Message message = UnitTestHelper.createTestMessage();
        message.setTimestamp(null);

        testSingleConstraint(message, "must not be null", "timestamp");
    }

    @Test 
    void testSenderNotNullConstraint () {
        Message message = UnitTestHelper.createTestMessage();
        message.setSender(null);

        testSingleConstraint(message, "must not be null", "sender");
    }

    @Test 
    void testMessageTypeSizeConstraints () {
        Message message = UnitTestHelper.createTestMessage();
        message.setMessageType("x".repeat(33));

        testSingleConstraint(message, "size must be between 0 and 32", "messageType");
    }

    @Test 
    void testMessageTypeNotBlankConstraints () {
        Message message = UnitTestHelper.createTestMessage();
        message.setMessageType(" ");

        testSingleConstraint(message, "must not be blank", "messageType");
    }

    @Test
    void testRelatinshipToSession() {
        Message message = UnitTestHelper.createTestMessage();
        Session session = message.getSession();
        session.setMessages(Set.of(message));

        assertTrue(session.getMessages().contains(message));
    }

    private void testSingleConstraint(Message message, String expectedMessage, String propertyName) {
        Set<ConstraintViolation<Message>> violations = validator.validate(message);

        assertThat(violations).hasSize(1);
        violations.forEach(action -> {
            assertThat(action.getMessage()).isEqualTo(expectedMessage);
            assertThat(action.getPropertyPath()).hasToString(propertyName);
        });
    }
}