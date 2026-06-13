package hse_st_group1.esbot.dto;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SessionDTO {
    private UUID sessionID;
    private UUID userId;
    private Instant startedAt;
    private Instant lastAccessed;
    private List<MessageDTO> messages;
    private List<QuizRequestDTO> quizRequests;
}
