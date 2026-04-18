package hse_st_group1.esbot.services;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import hse_st_group1.esbot.model.QuizItem;
import hse_st_group1.esbot.model.QuizRequest;
import hse_st_group1.esbot.repository.QuizItemRepository;
import hse_st_group1.esbot.repository.QuizRequestRepository;
import jakarta.transaction.Transactional;

@Service
public class QuizRequestService {

    private final QuizRequestRepository quizRequestRepository;
    private final QuizItemRepository quizItemRepository;
    private final AIService aiService;

    public QuizRequestService(QuizRequestRepository quizRequestRepository, AIService aiService, QuizItemRepository quizItemRepository){
        this.aiService = aiService;
        this.quizRequestRepository = quizRequestRepository;
        this.quizItemRepository = quizItemRepository;
    }
    @Transactional
    public QuizRequest createQuiz(QuizRequest quizRequest){
        List<String> questions;

        if(aiService.isAvailable()){
            questions = aiService.createQuestions(quizRequest.getQuizRequestContent());
        }
        else{
            throw new RuntimeException("Error: Quiz service is currently unavailable");
        }

        List<QuizItem> items = new ArrayList<>();

        quizRequestRepository.save(quizRequest);

        for(String question: questions){
            QuizItem item = new QuizItem(null, quizRequest, question, null, null);
            items.add(item);
            quizItemRepository.save(item);
        }
        quizRequest.setQuizItems(items);
        
        return quizRequestRepository.save(quizRequest);
    }
}
