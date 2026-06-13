package hse_st_group1.esbot.dto;

import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuizItemDTO {
    private UUID quizItemID;
    private UUID quizRequestID;
    private String question;
    private List<QuizAnswerDTO> quizAnswers;
    private List<QuizEvaluationDTO> quizEvaluations;
}
