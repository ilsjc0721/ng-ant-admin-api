package model.dto.other;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class InsertRevenue {

    private Integer orderId;

    private String payment;

    private String type;

    private Integer revenue;

    private Integer revenueUser;

    private Timestamp revenueDate;

}
