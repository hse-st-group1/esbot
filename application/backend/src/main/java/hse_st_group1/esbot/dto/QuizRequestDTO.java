package hse_st_group1.esbot.dto;

import hse_st_group1.esbot.model.QuizRequest.Difficulty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuizRequestDTO {
    //Topic
    private String quizRequestContent;
    //Number of questions
    private Integer count;
    //Difficulty of questions
    private Difficulty difficulty;
}
