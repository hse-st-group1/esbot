package hse_st_group1.esbot.services;

import java.time.Instant;

import org.springframework.stereotype.Service;

import hse_st_group1.esbot.AIServiceUnavailableException;
import hse_st_group1.esbot.NoQuizTopicProvidedException;
import hse_st_group1.esbot.model.Message;
import hse_st_group1.esbot.model.QuizAnswer;
import hse_st_group1.esbot.model.QuizEvaluation;
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
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ChatService {
    private final MessageRepository messageRepository;
    private final QuizRequestRepository quizRequestRepository;
    private final QuizItemRepository quizItemRepository;
    private final QuizEvaluationRepository quizEvaluationRepository;
    private final QuizAnswerRepository quizAnswerRepository;
    private final SessionRepository sessionRepository;
    
    private final AIService aiService;

    public ChatService(final SessionRepository sessionRepository, final MessageRepository messageRepository, final AIService aiService, final QuizItemRepository quizItemRepository, final QuizRequestRepository quizRequestRepository, final QuizAnswerRepository quizAnswerRepository, final QuizEvaluationRepository quizEvaluationRepository){
        this.sessionRepository = sessionRepository;
        this.messageRepository = messageRepository;
        this.aiService = aiService;
        this.quizRequestRepository = quizRequestRepository;
        this.quizItemRepository = quizItemRepository;
        this.quizAnswerRepository = quizAnswerRepository;
        this.quizEvaluationRepository = quizEvaluationRepository;
    }

    public User createUser (String userName) {
        final User user = new User();
        user.setUserName(userName);
        return user;
    }

    public Session createNewSession(final User user, String title){
        final Session session = new Session();
        session.setUser(user);
        session.setSessionTitle(title);
        session.setStartedAt(Instant.now());
        session.setLastAccessed(Instant.now());
        user.getSessions().add(session);
        sessionRepository.save(session);
        return session;
    }

    public Message sendMessage(final Session session, final String messageContent){
        final MessageService messageService = new MessageService(messageRepository, aiService);
        final Message message = new Message();
        message.setMessageType("Message");
        message.setMessageContent(messageContent);
        message.setSession(session);
        message.setSender(false);
        message.setTimestamp(Instant.now());
        session.getMessages().add(message);
        try{
            return messageService.sendMessage(message);
        }
        catch(AIServiceUnavailableException messageServiceUnavailableException){
            if(log.isErrorEnabled()){
                log.error(messageServiceUnavailableException.getMessage(), messageServiceUnavailableException);
            }
            throw messageServiceUnavailableException;
        }
    }

    public QuizRequest sendQuizRequest(final String quizRequestContent, final Session session, final Integer count, final Difficulty difficulty){
        final QuizRequestService quizRequestService = new QuizRequestService(quizRequestRepository, aiService, quizItemRepository);
        final QuizRequest quizRequest = new QuizRequest();
        if(quizRequestContent.isBlank() || quizRequestContent.isEmpty()){
            throw new NoQuizTopicProvidedException("Error: No quiz topic provided.");
        }
        else{
            quizRequest.setQuizRequestContent("Topic: " + quizRequestContent);
            quizRequest.setSession(session);
            quizRequest.setQuizItemCount(count);
            quizRequest.setQuizItemDifficulty(difficulty);
            try{
                return quizRequestService.createQuiz(quizRequest);
            }
            catch(AIServiceUnavailableException quizRequestserviceUnavailableException){
                if(log.isErrorEnabled()){
                    log.error(quizRequestserviceUnavailableException.getMessage(), quizRequestserviceUnavailableException);
                }
                throw quizRequestserviceUnavailableException;
            }
        }
    }

    public QuizEvaluation receiveEvaluation(final String answer, final QuizItem quizItem){
        final QuizEvaluationService quizEvaluationService = new QuizEvaluationService(quizAnswerRepository, aiService, quizEvaluationRepository);
        final QuizAnswer quizAnswer = new QuizAnswer();
        quizAnswer.setAnswer(answer);
        quizAnswer.setQuizItem(quizItem);
        quizAnswer.setTimeStamp(Instant.now());
        quizAnswerRepository.save(quizAnswer);
        try{
           return quizEvaluationService.evaluate(quizAnswer);
        }
        catch(AIServiceUnavailableException quizEvaluationServiceUnavailableException){
            if(log.isErrorEnabled()){
                log.error(quizEvaluationServiceUnavailableException.getMessage(), quizEvaluationServiceUnavailableException);
            }
            throw quizEvaluationServiceUnavailableException;
        }
    }
}
