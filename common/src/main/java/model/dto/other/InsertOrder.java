package model.dto.other;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class InsertOrder {
    @NotBlank
    private Integer roomId;

    @NotBlank
    private String type;

    @NotBlank
    private Date inDate;

    @NotBlank
    private String outDate;

    @NotBlank
    private Integer price;

    @NotBlank
    private Integer deposit;

    @NotBlank
    private Integer customerId;

    @NotBlank
    private String status;

    private Integer updateUser;
}
