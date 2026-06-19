package hse_st_group1.esbot.dto;

import java.time.Instant;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SessionMetadataDTO {
    private UUID sessionID;
    private UUID userID;
    private String sessionTitle;
    private Instant startedAt;
    private Instant lastAccessed;
}
