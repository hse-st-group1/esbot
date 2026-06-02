package hse_st_group1.esbot.controller;

import hse_st_group1.esbot.repository.MessageRepository;
import hse_st_group1.esbot.repository.QuizItemRepository;
import hse_st_group1.esbot.repository.SessionRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import hse_st_group1.esbot.model.Message;
import hse_st_group1.esbot.model.QuizItem;
import hse_st_group1.esbot.dto.QuizRequestDTO;
import hse_st_group1.esbot.model.QuizRequest;
import hse_st_group1.esbot.model.Session;
import hse_st_group1.esbot.model.User;
import hse_st_group1.esbot.repository.UserRepository;
import hse_st_group1.esbot.services.ChatService;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/sessions")
@RequiredArgsConstructor
public class ChatServiceController {

    public record QuizItemIdAndQuestion(UUID quizItemId, String quizItemQuestion) {}
    public record MessageIdAndContent(UUID messageid, String messageQuestion) {}

    private final SessionRepository sessionRepository;
    public final ChatService chatService;
    public final UserRepository userRepository;
    public final QuizItemRepository quizItemRepository;
    public final MessageRepository messageRepository;


    @PostMapping()
    public ResponseEntity<UUID> createSession(@RequestBody UUID userID) {

        User user = userRepository.findById(userID).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Session session = chatService.createNewSession(user);
        return ResponseEntity.ok(session.getSessionID());
    }

    
    @GetMapping()
    public List<UUID> getIdsOfAllSessionsForUser(@RequestBody UUID userID) {

        User user = userRepository.findById(userID).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        
        List<Session> sessions = sessionRepository.findByUser(user);

        if (sessions.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else {
            List<UUID> listOfSessionIds = new ArrayList<>();
            for (Session session : sessions) {
                listOfSessionIds.add(session.getSessionID());   
            }
            return listOfSessionIds;
        }
    }


    @PostMapping("/{sessionId}/messages")
    public ResponseEntity<String> sendMessage(@PathVariable UUID sessionId, @RequestBody String messageContenString) {        
        
        Session session = sessionRepository.findById(sessionId).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Message responseLLM = chatService.sendMessage(session, messageContenString);
        return ResponseEntity.ok(responseLLM.getMessageContent());
        //To Do at a later date: Implement a DTO for Message response to be able to send the response entity
    }


    @GetMapping("{sessionId}/messages")
    public List<MessageIdAndContent> getAllMessagesForSession(@PathVariable UUID sessionId) {
        List<MessageIdAndContent> listOfMessageidsAndContent = new ArrayList<>();

        Session session = sessionRepository.findById(sessionId).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        List<Message> messages = messageRepository.findBySessionOrderByTimestamp(session);

        if (messages.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else {
            for (Message message : messages) {
                MessageIdAndContent messageIdAndContent = new MessageIdAndContent(message.getMessageID(), message.getMessageContent());
                listOfMessageidsAndContent.add(messageIdAndContent);
            }
        }
        return listOfMessageidsAndContent;
    }
    

    @PostMapping("/{sessionId}/quiz")
    public String createQuiz(@PathVariable UUID sessionId, @RequestBody QuizRequestDTO quizRequestDTO) {
        Session session = sessionRepository.findById(sessionId).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        
            QuizRequest quizRequest = chatService.sendQuizRequest(quizRequestDTO.getQuizRequestContent(), session, quizRequestDTO.getCount(), quizRequestDTO.getDifficulty());

            List<QuizItem> quizItems = quizRequest.getQuizItems();
            List<QuizItemIdAndQuestion> listOfQuizItemIdsAndQuestions = new ArrayList<>();
            for (QuizItem item : quizItems) {
                QuizItemIdAndQuestion quizItemIdAndQuestion = new QuizItemIdAndQuestion(item.getQuizItemID(), item.getQuestion());
                listOfQuizItemIdsAndQuestions.add(quizItemIdAndQuestion);
            } 
        return listOfQuizItemIdsAndQuestions.toString();
    }
    

    @PostMapping("/{sessionId}/quiz/{quizItemId}/answer")
    public ResponseEntity<String> evaluateAnswerOfQuizItem(
        @PathVariable UUID sessionId,
        @PathVariable UUID quizItemId,
        @RequestBody String answer) {

        QuizItem item = quizItemRepository.findById(quizItemId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        
        String feedback = chatService.receiveEvaluation(answer, item)
            .getEvaluation();
        return ResponseEntity.ok(feedback);
    }
}
