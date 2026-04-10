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
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy=GenerationType.UUID)
    @Column(nullable = false)
    private UUID messageID;

    @ManyToOne
    @Column(nullable = false)
    private Session sessionID;

    @Column(nullable = false)
    private String messageContent;

    @Column(nullable = false)
    private Timestamp timestamp;

    @Column(nullable = false)
    private Boolean sender;

    @Column(nullable=false, length = 32)
    private String messageType;

}
