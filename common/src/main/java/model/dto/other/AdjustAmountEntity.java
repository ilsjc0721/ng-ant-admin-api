package model.dto.other;

import lombok.Data;
import java.io.Serializable;

@Data
public class AdjustAmountEntity implements Serializable {
    private Integer coachID;
    private Integer studentId;
    private Integer adjustAmount;
    private Integer coachFee;
    private Integer tuitionFee;
}
