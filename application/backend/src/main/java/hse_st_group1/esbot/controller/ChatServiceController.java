package hse_st_group1.esbot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import hse_st_group1.esbot.model.Session;
import hse_st_group1.esbot.model.User;
import hse_st_group1.esbot.repository.UserRepository;
import hse_st_group1.esbot.services.ChatService;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/sessions")
@RequiredArgsConstructor
public class ChatServiceController {

    public final ChatService chatService;
    public final UserRepository userRepository;
    

    @PostMapping()
    public ResponseEntity<UUID> postSession(@RequestBody UUID userID) {
        
        User user = userRepository.findById(userID).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Session session = chatService.createNewSession(user);
        return ResponseEntity.ok(session.getSessionID());
    }
    
}
