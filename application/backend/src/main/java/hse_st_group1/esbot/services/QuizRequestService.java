package hse_st_group1.esbot.services;

import java.util.List;

import org.springframework.stereotype.Service;

import hse_st_group1.esbot.AIServiceUnavailableException;
import hse_st_group1.esbot.NoQuizTopicProvidedException;
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

    public QuizRequestService(final QuizRequestRepository quizRequestRepository, final AIService aiService, final QuizItemRepository quizItemRepository){
        this.aiService = aiService;
        this.quizRequestRepository = quizRequestRepository;
        this.quizItemRepository = quizItemRepository;
    }

    @Transactional
    public QuizRequest createQuiz(final QuizRequest quizRequest){

        final String content = quizRequest.getQuizRequestContent();
            
        // If no topic was provided (-> no "about ...") then LLM should request topic
        if (content.contains("about") || content.contains("Topic")) {
            final List<String> questions;

            if(aiService.isAvailable()){
                questions = aiService.createQuestions(quizRequest.getQuizRequestContent());
            }
            else{
                throw new AIServiceUnavailableException("Error: Quiz service is currently unavailable");
            }

            //quizRequestRepository.save(quizRequest); //Redundant?
            final List<QuizItem> items = questions.stream().map(question -> new QuizItem(null, quizRequest, question, null, null)).toList();

            quizRequestRepository.save(quizRequest);
            quizItemRepository.saveAll(items);
            quizRequest.setQuizItems(items);
            return quizRequest;
        } else {
            throw new NoQuizTopicProvidedException("Error: No quiz topic provided.");
        }
    }
}
