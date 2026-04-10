
package hse_st_group1.esbot.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "QuizEvaluation")
@AllArgsConstructor
@Getter
@Setter
public class QuizEvaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column (nullable = false)
    private UUID evaluationID;

    @ManyToOne 
    @JoinColumn(updatable = false, nullable = false)
    private QuizItem quizItem;

    @ManyToOne
    @JoinColumn(updatable = false, nullable = false)
    private QuizAnswer quizAnswer;

    @Column (nullable = false)
    private String evaluation;
}