package hse_st_group1.esbot.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuizEvaluationDTO {
    private UUID evaluationID;
    private UUID quizAnswerID;
    private String evaluation;
}
