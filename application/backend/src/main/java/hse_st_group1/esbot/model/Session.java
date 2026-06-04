package hse_st_group1.esbot.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
// Getter & Setter Methods & Constructors do not need to be defined -> provided by Spring Lombok
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Session {
    @Id 
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private UUID sessionID;

    @NotNull
    @ManyToOne (optional = false) //cannot be null
    @JoinColumn(updatable = false) //cannot be overwritten
    private User user;

    @NotNull
    @Column(updatable = false, nullable = false)
    private Instant startedAt;

    @NotNull
    @Column(nullable = false)
    private Instant lastAccessed;

    @OneToMany (mappedBy = "session", cascade = CascadeType.REMOVE) //if the session is deleted, all corresponding messages are deleted too
    private List<Message> messages = new ArrayList<>();

    @OneToMany (mappedBy = "session", cascade = CascadeType.REMOVE)
    private Set<QuizRequest> quizRequests = new HashSet<>();

}
