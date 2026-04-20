package hse_st_group1.esbot.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import hse_st_group1.esbot.model.QuizAnswer;

@Repository
public interface QuizAnswerRepository extends JpaRepository<QuizAnswer, UUID>{

}
