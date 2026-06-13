package hse_st_group1.esbot.dto;

import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageDTO {
    private UUID messageID;
    private UUID sessionID;
    private String messageContent;
    private Instant timestamp;
    private Boolean sender;
    private String messageType;
}
