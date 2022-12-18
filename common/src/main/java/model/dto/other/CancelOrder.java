package model.dto.other;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Data
public class CancelOrder {
    @NotNull
    private Integer id;

    private Integer cash;

    private Integer card;

    private String memo;

    private Integer updateUser;

    private Timestamp updateTime;
}
