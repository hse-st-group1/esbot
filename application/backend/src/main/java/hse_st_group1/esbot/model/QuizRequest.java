package hse_st_group1.esbot.model;

import java.util.ArrayList;
import java.util.List;
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
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "QuizRequest")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class QuizRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column (nullable = false)
    @NotNull
    private UUID quizID;

    @ManyToOne 
    @NotNull
    @JoinColumn(updatable = false, nullable = false)
    private Session session;
    
    @Column (nullable = false)
    @NotBlank
    private String quizRequestContent;

    @OneToMany (mappedBy = "quizRequest", cascade = CascadeType.REMOVE)
    private List<QuizItem> quizItems = new ArrayList<>();
}
