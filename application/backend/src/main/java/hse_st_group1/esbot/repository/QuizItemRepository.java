package hse_st_group1.esbot.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import hse_st_group1.esbot.model.QuizItem;

public interface QuizItemRepository extends JpaRepository<QuizItem, UUID>{
    
}
