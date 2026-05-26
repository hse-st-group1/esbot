package hse_st_group1.esbot.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import hse_st_group1.esbot.model.Message;
import hse_st_group1.esbot.model.Session;

public interface MessageRepository extends JpaRepository<Message, UUID> {
    List<Message> findBySessionOrderByTimestamp(Session session);
}
