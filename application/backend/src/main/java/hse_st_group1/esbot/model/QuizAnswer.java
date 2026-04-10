package hse_st_group1.esbot.model;

import java.sql.Timestamp;
import java.util.UUID;
import jakarta.persistence.*;
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

    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID quizAnswerID;

    @OneToOne
    @JoinColumn(name ="quizItemID")
    private QuizItem quizItem;

    @Column
    private String answer;

    @Column
    private Timestamp timeStamp;

}
