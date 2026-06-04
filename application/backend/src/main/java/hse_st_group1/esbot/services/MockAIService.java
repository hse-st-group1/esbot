package hse_st_group1.esbot.services;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class MockAIService implements AIService {

    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public String responseString(final String input) {
        return "Mock response: " + input;
    }

    @Override
    public String evaluateAnswer(final String input) {
        return "Good answer (mock)";
    }

    @Override
    public List<String> createQuestions(final String topic) {
        return List.of("Question 1 about " + topic, "Question 2 about " + topic);
    }
}