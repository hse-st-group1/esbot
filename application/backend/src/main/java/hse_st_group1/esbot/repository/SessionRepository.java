package hse_st_group1.esbot.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import hse_st_group1.esbot.model.Session;
import hse_st_group1.esbot.model.User;

public interface SessionRepository extends JpaRepository<Session, UUID> {
    Session findBySessionID(UUID sessionID);
    List<Session> findByUser(User user);
}