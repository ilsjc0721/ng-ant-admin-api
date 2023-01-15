package model.dto.other;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ClassFeeResponse {
    private String name;
    private String feeType;
    private BigDecimal hours;
    private Integer totalAmount;
    private String nameCh;
    private Integer studentId;
    private Integer coachId;
    private Integer coachFee;
    private Integer tuitionFee;
}
