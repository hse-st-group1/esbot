package hse_st_group1.esbot.model;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import jakarta.validation.constraints.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class User {
    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    @NotNull
    private UUID userID; 

    @NotEmpty
    @Size(min = 2, max = 16, message = "{validation.name.size}")
    @Column (nullable = false)
    private String userName;

    @OneToMany (mappedBy = "user", cascade = CascadeType.REMOVE)
    private Set<Session> sessions = new HashSet<>();
}
