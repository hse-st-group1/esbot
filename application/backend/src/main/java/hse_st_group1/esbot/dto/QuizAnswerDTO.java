package hse_st_group1.esbot.dto;

import java.time.Instant;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuizAnswerDTO {
    private UUID quizAnswerID;
    private String answer;
    private Instant timeStamp;
    
}
