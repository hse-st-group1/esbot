package hse_st_group1.esbot.services;

import java.util.List;

public interface AIService {
    boolean isAvailable();
    String responseString(String input);
    String evaluateAnswer(String input);
    List<String> createQuestions(String topic);
}
