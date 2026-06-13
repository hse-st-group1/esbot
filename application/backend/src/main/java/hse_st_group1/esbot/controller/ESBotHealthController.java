package hse_st_group1.esbot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/health")
@RequiredArgsConstructor
public class ESBotHealthController {
    @GetMapping
    public ResponseEntity<String> livenessCheck () {
        return ResponseEntity.ok("Backend is running and reachable");
    }
    
}
