package hse_st_group1.esbot.dto;

import hse_st_group1.esbot.model.QuizRequest.Difficulty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuizRequestDTO {
    String quizRequestContent;
    Integer count;
    Difficulty difficulty;
}
