package hse_st_group1.esbot.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import hse_st_group1.esbot.model.QuizRequest;

public interface QuizRequestRepository extends JpaRepository<QuizRequest, UUID> {

}
