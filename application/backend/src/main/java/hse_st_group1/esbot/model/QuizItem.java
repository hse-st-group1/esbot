package hse_st_group1.esbot.model;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuizItem {
    @Id 
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private UUID quizItemID;

    @NotNull
    @ManyToOne (optional = false) //cannot be null
    @JoinColumn(updatable = false) //cannot be overwritten
    private QuizRequest quizRequest;

    @NotEmpty
    @Column (nullable = false)
    private String question;

    @OneToMany (mappedBy = "quizItem", cascade = CascadeType.REMOVE)
    private Set<QuizAnswer> quizAnswers = new HashSet<>();

    @OneToMany (mappedBy = "quizItem", cascade = CascadeType.REMOVE)
    private Set<QuizEvaluation> quizEvaluations = new HashSet<>();
}
