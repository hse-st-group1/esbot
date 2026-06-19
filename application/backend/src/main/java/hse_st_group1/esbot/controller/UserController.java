package hse_st_group1.esbot.controller;

import java.net.URI;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import hse_st_group1.esbot.model.User;
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
    public ResponseEntity<UUID> createUser(@RequestBody String userName) {
        User user = chatService.createUser(userName);

        if (user.getUserID() == null) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }

        final URI location = URI.create(USERBASEURL);
        return ResponseEntity.created(location).body(user.getUserID());
    }
}
