package model.dto.other;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class UpdateOrder {
    @NotNull
    private Integer id;

    private Integer roomId;

    private String type;

    private Date inDate;

    private Date outDate;

    private Integer price;

    private Integer deposit;

    private Integer customerId;

    private String status;

    private Integer updateUser;
}
