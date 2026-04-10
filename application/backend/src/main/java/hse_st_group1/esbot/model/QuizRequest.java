package hse_st_group1.esbot.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "QuizRequest")
@AllArgsConstructor
public class QuizRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column (nullable = false)
    private UUID quizID;

    @ManyToOne 
    @Column(updatable = false, nullable = false)
    private UUID sessionID;
    
    @Column (nullable = false)
    private String quizRequest;
}
