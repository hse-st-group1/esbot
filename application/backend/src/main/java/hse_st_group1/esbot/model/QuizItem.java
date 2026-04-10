package hse_st_group1.esbot.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class QuizItem {
    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID quizItemID;

    @ManyToOne (optional = false) //cannot be null
    @JoinColumn(name = "quizID", updatable = false) //cannot be overwritten
    private QuizRequest QuizRequest;

    @Column (nullable = false)
    private String question;
}
