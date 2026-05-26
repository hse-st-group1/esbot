package hse_st_group1.esbot.services;

import org.springframework.stereotype.Service;

import hse_st_group1.esbot.AIServiceUnavailableException;
import hse_st_group1.esbot.AnswerEmptyException;
import hse_st_group1.esbot.model.QuizAnswer;
import hse_st_group1.esbot.model.QuizEvaluation;
import hse_st_group1.esbot.repository.QuizAnswerRepository;
import hse_st_group1.esbot.repository.QuizEvaluationRepository;



@Service
public class QuizEvaluationService {

    public final QuizAnswerRepository quizAnswerRepository;
    public final AIService aiService;
    public final QuizEvaluationRepository quizEvaluationRepository;

    public QuizEvaluationService(QuizAnswerRepository quizAnswerRepository, AIService aiService, QuizEvaluationRepository quizEvaluationRepository){
        this.quizAnswerRepository = quizAnswerRepository;
        this.aiService = aiService;
        this.quizEvaluationRepository = quizEvaluationRepository;
    }

    public QuizEvaluation evaluate(QuizAnswer answer) {
        if(answer.getAnswer().isBlank() || answer.getAnswer().isEmpty()){
            throw new AnswerEmptyException("Error: Answer provided is empty");
        }
        if(aiService.isAvailable()){
            String evaluation = aiService.evaluateAnswer(answer.getAnswer());
            QuizEvaluation quizEvaluation = new QuizEvaluation(null, answer.getQuizItem(), answer, evaluation);
            quizEvaluationRepository.save(quizEvaluation);
            return quizEvaluation;
        }
        else{
            throw new AIServiceUnavailableException("Error: Evaluation service is currently unavailable");
        }
    }
}
