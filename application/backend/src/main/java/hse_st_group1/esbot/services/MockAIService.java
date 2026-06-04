package hse_st_group1.esbot.services;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class MockAIService implements AIService {

    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public String responseString(String input) {
        return "Mock response: " + input;
    }

    @Override
    public String evaluateAnswer(String input) {
        return "Good answer (mock)";
    }

    @Override
    public List<String> createQuestions(String topic) {
        return List.of("Question 1 about " + topic, "Question 2 about " + topic);
    }
}