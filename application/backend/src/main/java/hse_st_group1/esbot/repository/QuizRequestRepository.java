package hse_st_group1.esbot.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hse_st_group1.esbot.model.QuizRequest;

@Repository
public interface QuizRequestRepository extends JpaRepository<QuizRequest, UUID> {

}
