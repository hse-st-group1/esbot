package hse_st_group1.esbot.services;

import java.sql.Timestamp;

import org.springframework.stereotype.Service;

import hse_st_group1.esbot.AIServiceUnavailableException;
import hse_st_group1.esbot.model.Message;
import hse_st_group1.esbot.model.QuizAnswer;
import hse_st_group1.esbot.model.QuizItem;
import hse_st_group1.esbot.model.QuizRequest;
import hse_st_group1.esbot.model.Session;
import hse_st_group1.esbot.model.User;
import hse_st_group1.esbot.model.QuizRequest.Difficulty;
import hse_st_group1.esbot.repository.MessageRepository;
import hse_st_group1.esbot.repository.QuizAnswerRepository;
import hse_st_group1.esbot.repository.QuizEvaluationRepository;
import hse_st_group1.esbot.repository.QuizItemRepository;
import hse_st_group1.esbot.repository.QuizRequestRepository;
import hse_st_group1.esbot.repository.SessionRepository;

@Service
public class ChatService {
    private MessageRepository messageRepository;
    private QuizRequestRepository quizRequestRepository;
    private QuizItemRepository quizItemRepository;
    private QuizEvaluationRepository quizEvaluationRepository;
    private QuizAnswerRepository quizAnswerRepository;
    private SessionRepository sessionRepository;
    private AIService aiService;

    private AIServiceUnavailableException exception;
    
    public ChatService(SessionRepository sessionRepository, MessageRepository messageRepository, AIService aiService, QuizItemRepository quizItemRepository, QuizRequestRepository quizRequestRepository, QuizAnswerRepository quizAnswerRepository, QuizEvaluationRepository quizEvaluationRepository){
        this.sessionRepository = sessionRepository;
        this.messageRepository = messageRepository;
        this.aiService = aiService;
        this.quizRequestRepository = quizRequestRepository;
        this.quizItemRepository = quizItemRepository;
        this.quizAnswerRepository = quizAnswerRepository;
        this.quizEvaluationRepository = quizEvaluationRepository;
    }

    public Session createNewSession(User user){
        Session session = new Session();
        session.setUser(user);
        session.setStartedAt(new Timestamp(System.currentTimeMillis()));
        session.setLastAccessed(new Timestamp(System.currentTimeMillis()));
        user.getSessions().add(session);
        return sessionRepository.save(session);
    }

    public Message sendMessage(Session session, String messageContent){
        MessageService messageService = new MessageService(messageRepository, aiService);
        Message message = new Message();
        message.setMessageContent(messageContent);
        message.setSession(session);
        message.setSender(false);
        message.setTimestamp(new Timestamp(System.currentTimeMillis()));
        session.getMessages().add(message);
        try{
            return messageService.sendMessage(message);
        }
        catch(AIServiceUnavailableException messageServiceUnavailableException){
            this.exception = messageServiceUnavailableException;
            throw exception;
        }
    }

    public void sendQuizRequest(String quizRequestContent, Integer count, Difficulty difficulty){
        QuizRequestService quizRequestService = new QuizRequestService(quizRequestRepository, aiService, quizItemRepository);
        QuizRequest quizRequest = new QuizRequest();
        quizRequest.setQuizRequestContent(quizRequestContent);
        quizRequest.setQuizItemCount(count);
        quizRequest.setQuizItemDifficulty(difficulty);
        try{
            quizRequestService.createQuiz(quizRequest);
        }
        catch(AIServiceUnavailableException quizRequestserviceUnavailableException){
            this.exception = quizRequestserviceUnavailableException;
        }
    }

    public void receiveEvaluation(String answer, QuizItem quizItem){
        QuizEvaluationService quizEvaluationService = new QuizEvaluationService(quizAnswerRepository, aiService, quizEvaluationRepository);
        QuizAnswer quizAnswer = new QuizAnswer();
        quizAnswer.setAnswer(answer);
        quizAnswer.setQuizItem(quizItem);
        quizAnswer.setTimeStamp(new Timestamp(System.currentTimeMillis()));
        quizAnswerRepository.save(quizAnswer);
        try{
           quizEvaluationService.evaluate(quizAnswer);
        }
        catch(AIServiceUnavailableException quizEvaluationServiceUnavailableException){
            this.exception = quizEvaluationServiceUnavailableException;
        }
    }
}
