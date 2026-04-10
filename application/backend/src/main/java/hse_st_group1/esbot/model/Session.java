package hse_st_group1.esbot.model;

import java.sql.Timestamp;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Session {
    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID sessionID;

    @ManyToOne
    private UUID userID;

    private Timestamp startedAt;

    private Timestamp lastAccessed;
}
