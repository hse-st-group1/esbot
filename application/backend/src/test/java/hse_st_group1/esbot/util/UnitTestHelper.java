package hse_st_group1.esbot.util;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import hse_st_group1.esbot.model.Message;
import hse_st_group1.esbot.model.QuizAnswer;
import hse_st_group1.esbot.model.QuizEvaluation;
import hse_st_group1.esbot.model.QuizItem;
import hse_st_group1.esbot.model.QuizRequest;
import hse_st_group1.esbot.model.Session;
import hse_st_group1.esbot.model.User;

public class UnitTestHelper {
    //User functions
    public static User userCreator(){
        UUID userId = UUID.randomUUID();
        String userName = "Max";
        return new User(userId, userName, null);
    }
    
    //Session functions
    public static Session sessionCreator(){
        UUID sessionId = UUID.randomUUID();
        User user = userCreator();
        Timestamp startedAt = new Timestamp(System.currentTimeMillis());
        Timestamp lastAccessed = new Timestamp(System.currentTimeMillis());
        Session session = new Session(sessionId, user, startedAt, lastAccessed, null, null);
        user.setSessions(new HashSet<Session>());
        return session;
    }

    public static Session sessionCreator(UUID sessionID){
        UUID sessionId = sessionID;
        User user = userCreator();
        Timestamp startedAt = new Timestamp(System.currentTimeMillis());
        Timestamp lastAccessed = new Timestamp(System.currentTimeMillis());
        Session session = new Session(sessionId, user, startedAt, lastAccessed, null, null);
        user.setSessions(Set.of(session));
        return session;
    }

    public static Session sessionCreator(User user){
        UUID sessionId = UUID.randomUUID();
        Timestamp startedAt = new Timestamp(System.currentTimeMillis());
        Timestamp lastAccessed = new Timestamp(System.currentTimeMillis());
        Session session = new Session(sessionId, user, startedAt, lastAccessed, null, null);
        user.setSessions(Set.of(session));
        return session;
    }
    
    //QuizRequest functions
    public static QuizRequest quizRequestCreator(){
        UUID quizRequestId = UUID.randomUUID();
        Session session = sessionCreator();
        String content = "Test";
        QuizRequest quizRequest = new QuizRequest(quizRequestId, session, content, null);
        session.setQuizRequests(Set.of(quizRequest));
        return quizRequest;
    }
    public static QuizRequest quizRequestCreator(Session session){
        UUID quizRequestId = UUID.randomUUID();
        String content = "Test";
        QuizRequest quizRequest = new QuizRequest(quizRequestId, session, content, null);
        session.setQuizRequests(Set.of(quizRequest));
        return quizRequest;
    }

    //QuizItem functions
    public static QuizItem quizItemCreator(){
        UUID quizItemId = UUID.randomUUID();
        QuizRequest quizRequest = quizRequestCreator();
        String question = "Question";
        QuizItem quizItem = new QuizItem(quizItemId, quizRequest, question, null, null);
        quizItem.setQuizAnswers(Set.of(quizAnswerCreator(quizItem)));
        return quizItem;
    }
    public static QuizItem quizItemCreator(UUID quizItemId){
        QuizRequest quizRequest = quizRequestCreator();
        String question = "Question";
        QuizItem quizItem = new QuizItem(quizItemId, quizRequest, question, null, null);
        quizItem.setQuizAnswers(Set.of(quizAnswerCreator(quizItem)));
        return quizItem;
    }
    public static QuizItem quizItemCreator(QuizRequest quizRequest){
        UUID quizItemId = UUID.randomUUID();
        String question = "Question";
        QuizItem quizItem = new QuizItem(quizItemId, quizRequest, question, null, null);
        quizItem.setQuizAnswers(Set.of(quizAnswerCreator(quizItem)));
        quizRequest.setQuizItems(Set.of(quizItem));
        return quizItem;
    }

    //QuizAnswer functions
    public static QuizAnswer quizAnswerCreator(QuizItem item){
        UUID quizAnswerId = UUID.randomUUID();
        QuizItem quizItem = item;
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String answer = "My Solution";
        QuizAnswer quizAnswer = new QuizAnswer(quizAnswerId, quizItem, answer, timestamp);
        quizItem.setQuizAnswers(Set.of(quizAnswer));
        return quizAnswer;
    }

    //QuizEvaluation functions
    public static QuizEvaluation quizEvaluationCreator(){
        UUID evaluationId = UUID.randomUUID();
        QuizItem quizItem = quizItemCreator();
        QuizAnswer quizAnswer = quizAnswerCreator(quizItem);
        String evaluation = "Correct";
        QuizEvaluation quizEvaluation = new QuizEvaluation(evaluationId, quizItem, quizAnswer, evaluation);
        quizItem.setQuizEvaluations(Set.of(quizEvaluation));
        return quizEvaluation;
    }
    public static QuizEvaluation quizEvaluationCreator(QuizItem quizItem){
        UUID evaluationId = UUID.randomUUID();
        QuizAnswer quizAnswer = quizAnswerCreator(quizItem);
        String evaluation = "Correct";
        QuizEvaluation quizEvaluation = new QuizEvaluation(evaluationId, quizItem, quizAnswer, evaluation);
        quizItem.setQuizEvaluations(Set.of(quizEvaluation));
        return quizEvaluation;
    }    

    //Message functions
    public static Message createTestMessage() {
        return new Message(
            UUID.randomUUID(),
            UnitTestHelper.sessionCreator(),
            "Valid Message!",
            Timestamp.valueOf("2026-04-14 14:21:00"),
            false,
            "TEST"
        );
    }

    public static Message createTestMessage(Session session) {
        return new Message(
            UUID.randomUUID(),
            session,
            "Valid Message!",
            Timestamp.valueOf("2026-04-14 14:21:00"),
            false,
            "TEST"
        );
    }

    public static Message createTestMessage(UUID sessionID) {
        return new Message(
            UUID.randomUUID(),
            UnitTestHelper.sessionCreator(sessionID),
            "Valid Message!",
            Timestamp.valueOf("2026-04-14 14:21:00"),
            false,
            "TEST"
        );
    }
}
