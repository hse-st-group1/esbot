package hse_st_group1.esbot.model;

import java.sql.Timestamp;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
// Getter & Setter Methods do not need to be defined -> provided by Spring Lombok
@Getter
@Setter
// Constructors do not need to be defined -> provided by Spring Lombok
@NoArgsConstructor
@AllArgsConstructor
public class Session {
    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID sessionID;

    @ManyToOne
    @Column(updatable = false, nullable = false) //cannot be null or overwritten
    private UUID userID;

    @Column(updatable = false, nullable = false)
    private Timestamp startedAt;

    @Column(nullable = true)
    private Timestamp lastAccessed;
}
