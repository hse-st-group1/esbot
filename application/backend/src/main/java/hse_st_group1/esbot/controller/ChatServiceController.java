package hse_st_group1.esbot.controller;

import hse_st_group1.esbot.repository.SessionRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import hse_st_group1.esbot.model.Message;
import hse_st_group1.esbot.dto.QuizRequestDTO;
import hse_st_group1.esbot.model.QuizRequest;
import hse_st_group1.esbot.model.Session;
import hse_st_group1.esbot.model.User;
import hse_st_group1.esbot.repository.UserRepository;
import hse_st_group1.esbot.services.ChatService;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/sessions")
@RequiredArgsConstructor
public class ChatServiceController {

    private final SessionRepository sessionRepository;
    public final ChatService chatService;
    public final UserRepository userRepository;
    

    @PostMapping()
    public ResponseEntity<UUID> postSession(@RequestBody UUID userID) {

        User user = userRepository.findById(userID).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Session session = chatService.createNewSession(user);
        return ResponseEntity.ok(session.getSessionID());
    }

    @PostMapping("/{sessionId}/messages")
    public ResponseEntity<String> sendMessage(@PathVariable UUID sessionId, @RequestBody String messageContenString) {        
        
        Session session = sessionRepository.findById(sessionId).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Message responseLLM = chatService.sendMessage(session, messageContenString);
        return ResponseEntity.ok(responseLLM.getMessageContent());
        //To Do at a later date: Implement a DTO for Message response to be able to send the response entity
    }
    @PostMapping("/{sessionId}/quiz")
    public String postQuiz(@PathVariable UUID sessionId, @RequestBody QuizRequestDTO quizRequestDTO) {
        Session session = sessionRepository.findById(sessionId).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        
            QuizRequest quizRequest = chatService.sendQuizRequest(quizRequestDTO.getQuizRequestContent(), session, quizRequestDTO.getCount(), quizRequestDTO.getDifficulty());

        return quizRequest.getQuizItems().toString();
    }
    
}
