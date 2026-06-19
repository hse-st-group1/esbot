package hse_st_group1.esbot.controller;

import java.net.URI;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hse_st_group1.esbot.converter.EntityToDTO;
import hse_st_group1.esbot.dto.CreateSessionDTO;
import hse_st_group1.esbot.model.User;
import hse_st_group1.esbot.repository.MessageRepository;
import hse_st_group1.esbot.repository.QuizItemRepository;
import hse_st_group1.esbot.repository.SessionRepository;
import hse_st_group1.esbot.repository.UserRepository;
import hse_st_group1.esbot.services.ChatService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@SuppressWarnings({"PMD.CouplingBetweenObjects", "PMD.ExcessiveImports"})

    

public class UserController {

    public final ChatService chatService;
    private static final String USERBASEURL = "/user/";

    @PostMapping()
    public ResponseEntity<UUID> createUser(String userName) {
        User user = chatService.createUser(userName);
        final URI location = URI.create(USERBASEURL);
        return ResponseEntity.created(location).body(user.getUserID());
    }
}
