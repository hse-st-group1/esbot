
package hse_st_group1.esbot.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotNull
    private UUID evaluationID;

    @ManyToOne 
    @JoinColumn(updatable = false, nullable = false)
    @NotNull
    private QuizItem quizItem;

    @OneToOne
    @JoinColumn(updatable = false, nullable = false)
    @NotNull
    private QuizAnswer quizAnswer;

    @Column (nullable = false)
    @NotBlank
    private String evaluation;
}