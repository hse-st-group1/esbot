package hse_st_group1.esbot.services;

import org.springframework.stereotype.Service;

import hse_st_group1.esbot.model.QuizAnswer;
import hse_st_group1.esbot.model.QuizEvaluation;
import hse_st_group1.esbot.repository.QuizAnswerRepository;
import hse_st_group1.esbot.repository.QuizEvaluationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor
public class QuizEvaluationService {

    public final QuizAnswerRepository quizAnswerRepository;
    public final AIService aiService;
    public final QuizEvaluationRepository quizEvaluationRepository;

    @Transactional
    public QuizEvaluation evaluate(QuizAnswer answer) {
        String evaluation = aiService.evaluateAnswer(answer.getAnswer());
        QuizEvaluation quizEvaluation = new QuizEvaluation(null, answer.getQuizItem(), answer, evaluation);
        quizEvaluationRepository.save(quizEvaluation);
        return quizEvaluation;
    }
}
