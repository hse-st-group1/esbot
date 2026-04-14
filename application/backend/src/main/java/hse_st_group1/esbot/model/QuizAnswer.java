package hse_st_group1.esbot.model;

import java.sql.Timestamp;
import java.util.UUID;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuizAnswer{

    @NotNull    
    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column (nullable = false)
    private UUID quizAnswerID;
    
    @NotNull
    @ManyToOne
    @JoinColumn(name ="quizItemID", nullable = false)
    private QuizItem quizItem;

    @Column
    @NotEmpty
    private String answer;

    @NotNull
    @Column (nullable = false)
    private Timestamp timeStamp;

}
