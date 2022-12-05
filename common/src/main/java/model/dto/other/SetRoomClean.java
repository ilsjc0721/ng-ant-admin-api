package model.dto.other;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Data
public class SetRoomClean {
    @NotNull
    private Integer userId;

    @NotNull
    private Integer roomId;

    @NotBlank
    private String type;

    @NotNull
    private Timestamp startDatetime;

    private Timestamp endDatetime;
}
