package model.dto.other;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class RoomCleanResponse implements Serializable {
    private Integer cleanId;

    private Integer userId;

    private Integer roomId;

    private String type;

    private Timestamp startDatetime;

    private Timestamp  endDatetime;

    private String status;

    private String roomName;

    private String userChName;
}
