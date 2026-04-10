package hse_st_group1.esbot.model;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
// Getter & Setter Methods do not need to be defined -> provided by Spring Lombok
@Getter
@Setter
public class Session {
    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private UUID sessionID;

    @ManyToOne (optional = false) //cannot be null
    @JoinColumn(updatable = false) //cannot be overwritten
    private User user;

    @Column(updatable = false, nullable = false)
    private Timestamp startedAt;

    @Column(nullable = false)
    private Timestamp lastAccessed;

    @OneToMany (mappedBy = "session", cascade = CascadeType.REMOVE) //if the session is deleted, all corresponding messages are deleted too
    private Set<Message> messages = new HashSet<>();

    @OneToMany (mappedBy = "session", cascade = CascadeType.REMOVE)
    private Set<QuizRequest> quizRequests = new HashSet<>();
}
