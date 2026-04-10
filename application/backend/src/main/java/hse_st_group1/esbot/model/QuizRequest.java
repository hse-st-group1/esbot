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
@Table(name = "QuizRequest")
@AllArgsConstructor
@Getter
@Setter
public class QuizRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column (nullable = false)
    private UUID quizID;

    @ManyToOne 
    @JoinColumn(updatable = false, nullable = false)
    private Session session;
    
    @Column (nullable = false)
    private String quizRequestContent;
}
