package hse_st_group1.esbot.model;

import java.sql.Timestamp;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @NotNull
    @JoinColumn(nullable = false)
    private Session session;

    @Column(nullable = false)
    @NotBlank
    private String messageContent;

    @NotNull
    @Column(nullable = false)
    private Timestamp timestamp;

    @NotNull
    @Column(nullable = false)
    private Boolean sender;

    @NotBlank @Size(max = 32)
    @Column(nullable=false, length = 32)
    private String messageType;
}
