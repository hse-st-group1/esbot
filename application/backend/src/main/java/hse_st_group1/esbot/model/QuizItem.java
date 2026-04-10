package hse_st_group1.esbot.model;

import java.util.UUID;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

public class QuizItem {
    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID quizItemID;

    @ManyToOne
    private UUID quizID;

    private String question;
}
