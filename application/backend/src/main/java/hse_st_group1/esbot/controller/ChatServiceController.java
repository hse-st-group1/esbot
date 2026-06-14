package hse_st_group1.esbot.controller;

import hse_st_group1.esbot.repository.MessageRepository;
import hse_st_group1.esbot.repository.QuizItemRepository;
import hse_st_group1.esbot.repository.SessionRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import hse_st_group1.esbot.converter.EntityToDTO;
import hse_st_group1.esbot.model.Message;
import hse_st_group1.esbot.model.QuizItem;
import hse_st_group1.esbot.dto.MessageDTO;
import hse_st_group1.esbot.dto.QuizRequestDTO;
import hse_st_group1.esbot.dto.SessionDTO;
import hse_st_group1.esbot.dto.SessionMetadataDTO;
import hse_st_group1.esbot.model.QuizRequest;
import hse_st_group1.esbot.model.Session;
import hse_st_group1.esbot.model.User;
import hse_st_group1.esbot.repository.UserRepository;
import hse_st_group1.esbot.services.ChatService;
import lombok.RequiredArgsConstructor;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/sessions")
@RequiredArgsConstructor
@SuppressWarnings({"PMD.CouplingBetweenObjects", "PMD.ExcessiveImports"})
public class ChatServiceController {

    private final SessionRepository sessionRepository;
    public final ChatService chatService;
    public final UserRepository userRepository;
    public final QuizItemRepository quizItemRepository;
    public final MessageRepository messageRepository;

    private final EntityToDTO entityToDTO;

    private static final String SESSIONBASEURL = "/sessions/";

    @PostMapping()
    public ResponseEntity<UUID> createSession(@RequestBody final UUID userID) {

        final User user = userRepository.findById(userID).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        final Session session = chatService.createNewSession(user);

        URI location = URI.create(SESSIONBASEURL + session.getSessionID().toString());
        return ResponseEntity.created(location).body(session.getSessionID());
    }

    
    @GetMapping()
    public List<UUID> getIdsOfAllSessionsForUser(@RequestBody final UUID userID) {

        final User user = userRepository.findById(userID).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        
        final List<Session> sessions = sessionRepository.findByUser(user);

        if (sessions.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else {
            final List<UUID> listOfSessionIds = new ArrayList<>();
            for (final Session session : sessions) {
                listOfSessionIds.add(session.getSessionID());   
            }
            return listOfSessionIds;
        }
    }

    @GetMapping("{sessionId}")
    public ResponseEntity<SessionMetadataDTO> getSessionMetadata(@PathVariable final UUID sessionId, @RequestBody final UUID userId) { 
        
        final Session session = sessionRepository.findById(sessionId).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        // Check if Session belongs to requesting user
        if (!session.getUser().getUserID().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        } 
                        
        SessionMetadataDTO sessionMetadataDTO = new SessionMetadataDTO(
            sessionId,
            session.getUser().getUserID(),
            session.getStartedAt(),
            session.getLastAccessed()
        );
        return ResponseEntity.ok(sessionMetadataDTO);
    }

    @GetMapping("{sessionId}/complete")
    public ResponseEntity<SessionDTO> getCompleteSessionData(@PathVariable final UUID sessionId, @RequestBody final UUID userId) { 
        
        Session session = sessionRepository.findById(sessionId).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        // Check if Session belongs to requesting user
        if (!session.getUser().getUserID().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        } 
        
        List<Message> messages = session.getMessages();
        List<MessageDTO> messageDTOs = entityToDTO.messagesToMessageDTOs(messages);

        Set<QuizRequest> quizRequests = session.getQuizRequests();
        List<QuizRequestDTO> quizRequestDTOs = entityToDTO.quizRequestsToQuizRequestDTOs(quizRequests);

        SessionDTO sessionDTO = new SessionDTO(
            sessionId,
            session.getUser().getUserID(),
            session.getStartedAt(),
            session.getLastAccessed(),
            messageDTOs,
            quizRequestDTOs
        );
        return ResponseEntity.ok(sessionDTO);        
    }

    @DeleteMapping("{sessionId}")
    public ResponseEntity<String> deleteSession(@PathVariable final UUID sessionId, @RequestBody final UUID userId) {
        
        Session session = sessionRepository.findById(sessionId).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!session.getUser().getUserID().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        } 
        
        sessionRepository.deleteById(sessionId);
        return ResponseEntity.ok("Session sucessfully deleted.");
    }
    


    @PostMapping("/{sessionId}/messages")
    public ResponseEntity<MessageDTO> sendMessage(@PathVariable final UUID sessionId, @RequestBody final String messageContenString) {        
        
        final Session session = sessionRepository.findById(sessionId).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        //To Do at a later date: Implement User Check

        final Message responseLLM = chatService.sendMessage(session, messageContenString);

        MessageDTO resonseAsDTO = new MessageDTO(
            responseLLM.getMessageID(), 
            sessionId, 
            responseLLM.getMessageContent(), 
            responseLLM.getTimestamp(), 
            responseLLM.getSender(), 
            responseLLM.getMessageType()
        );
        URI location = URI.create(SESSIONBASEURL + session.getSessionID().toString() + "/messages");
        return ResponseEntity.created(location).body(resonseAsDTO);
        
    }


    @GetMapping("{sessionId}/messages")
    public ResponseEntity<List<MessageDTO>> getAllMessagesForSession(@PathVariable final UUID sessionId, @RequestBody final UUID userId) {
        final Session session = sessionRepository.findById(sessionId).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            
        // Check if Session belongs to requesting user
        if (!session.getUser().getUserID().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        } 

        final List<Message> messages = messageRepository.findBySessionOrderByTimestamp(session);

        if (messages.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else {
            List<MessageDTO> messageDTOs = entityToDTO.messagesToMessageDTOs(messages);
            return ResponseEntity.ok(messageDTOs);
        }
    }
    

    @PostMapping("/{sessionId}/quiz")
    public ResponseEntity<QuizRequestDTO> createQuiz(@PathVariable final UUID sessionId, @RequestBody final QuizRequestDTO quizRequestDTO) {
        final Session session = sessionRepository.findById(sessionId).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        
        final QuizRequest quizRequest = chatService.sendQuizRequest(
            quizRequestDTO.getQuizRequestContent(), 
            session, 
            quizRequestDTO.getCount(), 
            quizRequestDTO.getDifficulty());
        
        URI location = URI.create(SESSIONBASEURL + sessionId + "/quiz");
        return ResponseEntity.created(location).body(entityToDTO.quizRequestToQuizRequestDTO(quizRequest));
    }
    

    @PostMapping("/{sessionId}/quiz/{quizItemId}/answer")
    public ResponseEntity<String> evaluateAnswerOfQuizItem(
        @PathVariable final UUID sessionId,
        @PathVariable final UUID quizItemId,
        @RequestBody final String answer) {

        final QuizItem item = quizItemRepository.findById(quizItemId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        
        final String feedback = chatService.receiveEvaluation(answer, item)
            .getEvaluation();
        
        URI location = URI.create(SESSIONBASEURL + sessionId + "quiz/" + quizItemId + "/answer");
        return ResponseEntity.created(location).body(feedback);
    }
}
