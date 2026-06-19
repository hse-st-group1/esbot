package hse_st_group1.esbot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.UUID;


@Data
@AllArgsConstructor
public class CreateSessionDTO {
    private UUID userID;
    private String sessionTitle;
}
