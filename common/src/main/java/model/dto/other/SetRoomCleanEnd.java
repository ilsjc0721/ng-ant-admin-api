package model.dto.other;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class SetRoomCleanEnd implements Serializable {
    @NotNull
    private Integer cleanId;

    @NotNull
    private Integer roomId;

    @NotNull
    private Timestamp endDatetime;

    @NotNull
    private Integer cost;

    private Integer updateUser;
}
