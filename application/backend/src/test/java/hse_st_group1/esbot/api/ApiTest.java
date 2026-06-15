package hse_st_group1.esbot.api;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import hse_st_group1.esbot.dto.SessionDTO;
import hse_st_group1.esbot.dto.SessionMetadataDTO;
import hse_st_group1.esbot.model.Session;
import hse_st_group1.esbot.model.User;
import hse_st_group1.esbot.repository.QuizEvaluationRepository;
import hse_st_group1.esbot.repository.QuizItemRepository;
import hse_st_group1.esbot.repository.SessionRepository;
import hse_st_group1.esbot.repository.UserRepository;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import io.restassured.RestAssured;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ApiTest {

    @LocalServerPort
    int port;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private QuizItemRepository quizItemRepository;

    @Autowired
    private QuizEvaluationRepository quizEvaluationRepository;

    private User testUser;

    @BeforeAll
    void setup() {

        RestAssured.port = port;
      
        testUser = new User();
        testUser.setUserName("Tester");
        userRepository.save(testUser);
    }

    @Test
    @Tag("smoke")
    @Tag("api")
    void testHealthCheck(){
        given()
        .when()
            .get("/health")
        .then()
            .statusCode(200)
            .body(equalTo("Backend is running and reachable"));
    }

    @Test
    @Tag("happy-path")
    @Tag("api")
    void testPostSession(){
        UUID sessionId = 
        given()
            .contentType("application/json")
            .body(("\"%s\"").formatted(testUser.getUserID()))
        .when()
            .post("/sessions")
        .then()
            .statusCode(201)
            .extract()
            .as(UUID.class);

        Session session = sessionRepository.findById(sessionId).orElseThrow();
        assertEquals(sessionId, session.getSessionID());
    }

    @Test
    @Tag("happy-path")
    @Tag("api")
    void testGetSessionMetaData(){
        Session session = new Session();
        session.setUser(testUser);
        session.setStartedAt(Instant.now().truncatedTo(ChronoUnit.MILLIS));
        session.setLastAccessed(Instant.now().truncatedTo(ChronoUnit.MILLIS));
        sessionRepository.save(session);

        SessionMetadataDTO sessionMetadata = given()
            .contentType("application/json")
            .body(("\"%s\"").formatted(testUser.getUserID()))
        .when()
            .get(("/sessions/%s").formatted(session.getSessionID()))
        .then()
            .statusCode(200)
            .extract()
            .as(SessionMetadataDTO.class);
        
        assertEquals(session.getSessionID(), sessionMetadata.getSessionID());
        assertEquals(session.getUser().getUserID(), sessionMetadata.getUserId());
        assertEquals(session.getStartedAt(), sessionMetadata.getStartedAt());
        assertEquals(session.getLastAccessed(), sessionMetadata.getLastAccessed());
    }

    @Test
    @Tag("happy-path")
    @Tag("api")
    void testGetSession(){
        Session session = new Session();
        session.setUser(testUser);
        session.setStartedAt(Instant.now().truncatedTo(ChronoUnit.MILLIS));
        session.setLastAccessed(Instant.now().truncatedTo(ChronoUnit.MILLIS));
        sessionRepository.save(session);

        SessionDTO returnedSession = given()
            .contentType("application/json")
            .body(("\"%s\"").formatted(testUser.getUserID()))
        .when()
            .get(("/sessions/%s/complete").formatted(session.getSessionID()))
        .then()
            .statusCode(200)
            .extract()
            .as(SessionDTO.class);
        
        assertEquals(session.getSessionID(), returnedSession.getSessionID());
        assertEquals(session.getUser().getUserID(), returnedSession.getUserId());
        assertEquals(session.getStartedAt(), returnedSession.getStartedAt());
        assertEquals(session.getLastAccessed(), returnedSession.getLastAccessed());
        assertThat(returnedSession.getMessages()).isEmpty();
        assertThat(returnedSession.getQuizRequests()).isEmpty();
    }

    @Test
    @Tag("happy-path")
    @Tag("api")
    void testDeleteSession(){
        Session session = new Session();
        session.setUser(testUser);
        session.setStartedAt(Instant.now().truncatedTo(ChronoUnit.MILLIS));
        session.setLastAccessed(Instant.now().truncatedTo(ChronoUnit.MILLIS));
        sessionRepository.save(session);

        assertTrue(sessionRepository.existsById(session.getSessionID()));

        given()
            .contentType("application/json")
            .body(("\"%s\"").formatted(testUser.getUserID()))
        .when()
            .delete(("/sessions/%s").formatted(session.getSessionID()))
        .then()
            .statusCode(200)
            .body(equalTo("Session sucessfully deleted."));

        assertFalse(sessionRepository.existsById(session.getSessionID()));
    }

    @Test
    @Tag("happy-path")
    @Tag("api")
    void testSendingAMessage() {
        Session session = new Session();
        session.setUser(testUser);
        session.setStartedAt(Instant.now().truncatedTo(ChronoUnit.MILLIS));
        session.setLastAccessed(Instant.now().truncatedTo(ChronoUnit.MILLIS));
        sessionRepository.save(session);

        given()
            .contentType("application/json")
            .body("API-Test Message")
        .when()
            .post(("/sessions/%s/messages").formatted(session.getSessionID()))
        .then()
            .statusCode(201)
            .body("messageContent", not(emptyString()));
    }

    @Test
    @Tag("unhappy-path")
    @Tag("api")
    void testFakeUserID() {
        UUID fakeID = UUID.randomUUID();
        while(testUser.getUserID()==fakeID) {
            fakeID = UUID.randomUUID();
        }
        given()
            .contentType("application/json")
            .body(("\"%s\"").formatted(fakeID))
        .when()
            .post("/sessions")
        .then()
            .statusCode(404);
    }

    @Test
    @Tag("unhappy-path")
    @Tag("api")
    void testEmptyUserID() {
        given()
            .contentType("application/json")
            .body(("\"\""))
        .when()
            .post("/sessions")
        .then()
            .statusCode(400);
    }

    @Test
    @Tag("unhappy-path")
    @Tag("api")
    void testFakeSessionID(){
        Session session = new Session();
        session.setUser(testUser);
        session.setStartedAt(Instant.now().truncatedTo(ChronoUnit.MILLIS));
        session.setLastAccessed(Instant.now().truncatedTo(ChronoUnit.MILLIS));
        sessionRepository.save(session);

        UUID fakeID = UUID.randomUUID();
        while(session.getSessionID()==fakeID) {
            fakeID = UUID.randomUUID();
        }
        given()
            .contentType("application/json")
            .body(("\"%s\"").formatted(testUser.getUserID()))
        .when()
            .get(("/sessions/%s").formatted(fakeID))
        .then()
            .statusCode(404);
    }

    @Test
    @Tag("unhappy-path")
    @Tag("api")
    void testGetWithNoMessages() {
        Session session = new Session();
        session.setUser(testUser);
        session.setStartedAt(Instant.now().truncatedTo(ChronoUnit.MILLIS));
        session.setLastAccessed(Instant.now().truncatedTo(ChronoUnit.MILLIS));
        sessionRepository.save(session);

        given()
            .contentType("application/json")
            .body(("\"%s\"").formatted(testUser.getUserID()))
        .when()
            .get(("/sessions/%s/messages").formatted(session.getSessionID()))
        .then()
            .statusCode(200)
            .body("", hasSize(0));
    }


    @AfterAll
    void cleanup() {
        quizEvaluationRepository.deleteAll();
        quizItemRepository.deleteAll();
        sessionRepository.deleteAll();
        userRepository.deleteAll();
    }
}