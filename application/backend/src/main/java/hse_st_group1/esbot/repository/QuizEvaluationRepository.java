package hse_st_group1.esbot.repository;

import org.springframework.stereotype.Repository;

import hse_st_group1.esbot.model.QuizEvaluation;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface QuizEvaluationRepository extends JpaRepository<QuizEvaluation, UUID>{ 
}
