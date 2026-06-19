package hse_st_group1.esbot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import hse_st_group1.esbot.model.Message;
import hse_st_group1.esbot.model.Session;
import hse_st_group1.esbot.model.User;
import hse_st_group1.esbot.repository.MessageRepository;
import hse_st_group1.esbot.repository.SessionRepository;
import hse_st_group1.esbot.repository.UserRepository;

@DataJpaTest
class SessionRepoTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private MessageRepository messageRepository;

    private User user;
    private Instant timestamp = Instant.now();
    private UUID sessionID;
    private Session session;
    private Session newSession;

    @BeforeEach
    void createNewUser(){
        user = new User();
        user.setUserName("Tron");
        userRepository.save(user);
        session = new Session();
        session.setSessionTitle("TestSession");
        session.setStartedAt(Instant.now());
        session.setLastAccessed(Instant.now());
        session.setUser(user);
        sessionRepository.save(session);
        sessionID = session.getSessionID();
    }

    @Test 
    void createNewSession(){
        newSession = new Session();
        newSession.setSessionTitle("TestSession");
        newSession.setStartedAt(Instant.now());
        newSession.setLastAccessed(newSession.getStartedAt());
        newSession.setUser(user);
        Session savedSession = sessionRepository.save(newSession);
        assertNotNull(savedSession);
        assertEquals(savedSession, newSession);
    }

    @Test
    void findBySessionID(){
        newSession = sessionRepository.findBySessionID(session.getSessionID());
        assertEquals(session, newSession);
        assertEquals(sessionID, newSession.getSessionID());
    }

    @Test
    void createMessages(){
        List<Message> messages = new ArrayList<>();
        Message message = new Message();
        timestamp = Instant.now();

        message.setMessageType("Message");
        message.setSession(session);
        message.setSender(false);
        message.setTimestamp(Instant.now());
        message.setMessageContent("Hello To AI");

        messageRepository.save(message);
        messages.add(message);
        session.setMessages(messages);
        sessionRepository.save(session);

        message = new Message();
        timestamp = Instant.now();

        message.setMessageType("Message");
        message.setSession(session);
        message.setSender(true);
        message.setTimestamp(Instant.now());
        message.setMessageContent("Hello To Tron");

        messageRepository.save(message);
        messages.add(message);
        session.setMessages(messages);
        sessionRepository.save(session);

        message = new Message();
        timestamp = Instant.now();

        message.setMessageType("Message");
        message.setSession(session);
        message.setSender(true);
        message.setTimestamp(Instant.now());
        message.setMessageContent("AI Handshake Complete");

        messageRepository.save(message);
        messages.add(message);
        session.setMessages(messages);
        sessionRepository.save(session);

        List<Message> savedMessages = messageRepository.findBySessionOrderByTimestamp(session);
        assertEquals(messages.get(0).getMessageContent(), savedMessages.get(0).getMessageContent());
        assertEquals(messages.get(1).getMessageContent(), savedMessages.get(1).getMessageContent());
        assertEquals(messages.get(2).getMessageContent(), savedMessages.get(2).getMessageContent());
        assertNotEquals(messages.get(0).getMessageContent(), messages.get(1).getMessageContent());
        assertNotEquals(messages.get(1).getMessageContent(), messages.get(2).getMessageContent());
        assertNotEquals(messages.get(2).getMessageContent(), messages.get(0).getMessageContent());
    }

    @Test
    void updateTimestamp(){
        timestamp = Instant.now();
        sessionID = session.getSessionID();
        session.setLastAccessed(timestamp);
        session = null;
        session = sessionRepository.findBySessionID(sessionID);
        assertEquals(session.getLastAccessed(), timestamp);
    }

    @Test 
    void deleteSession(){
        session = sessionRepository.findBySessionID(sessionID);
        newSession = sessionRepository.findBySessionID(sessionID);
        assertEquals(session, newSession);
        sessionRepository.delete(session);
        session = sessionRepository.findBySessionID(sessionID);
        assertNull(session);
        assertNotEquals(session, newSession);
    }
}
