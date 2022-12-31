package model.dto.other;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

@Data
public class InsertOrder {

    private Integer roomId;

    private String type;

    private String payment;

    private Timestamp inDate;

    private Timestamp outDate;

    private Integer price;

    private Integer deposit;

    private Integer customerId;

    private String status;

    private String memo;

    private Integer updateUser;

    private Timestamp updateTime;
}
