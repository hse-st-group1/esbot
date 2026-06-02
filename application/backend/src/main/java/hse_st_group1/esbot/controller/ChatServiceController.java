package hse_st_group1.esbot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import hse_st_group1.esbot.model.Message;
import hse_st_group1.esbot.model.Session;
import hse_st_group1.esbot.model.User;
import hse_st_group1.esbot.repository.SessionRepository;
import hse_st_group1.esbot.repository.UserRepository;
import hse_st_group1.esbot.services.ChatService;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;




@RestController
@RequestMapping("/sessions")
@RequiredArgsConstructor
public class ChatServiceController {

    public final ChatService chatService;
    public final UserRepository userRepository;
    public final SessionRepository sessionRepository;
    

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
}
