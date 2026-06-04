package hse_st_group1.esbot.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import hse_st_group1.esbot.model.Session;
import hse_st_group1.esbot.model.User;

public interface SessionRepository extends JpaRepository<Session, UUID> {
    // Creating a new session is inherited from JpaRepository as save() 
    Session findBySessionID(UUID sessionID);
    List<Session> findByUser(User user);
    // Appending a new message to a session is handled by SessionService
    // Retrieving the full message history of a session is handled by MessageRepository
    // Updating session metadata is handled by save()
    // Deleting a session is inherited from JpaRepository as deleteByID(UUID sessionID)
}