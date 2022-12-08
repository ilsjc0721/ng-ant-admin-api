package model.dto.other;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class ReportResponse implements Serializable {
    private String customerName;
    private String roomName;
    private String orderType;
    private String payment;
    private String revenueType;
    private Integer revenue;
    private String revenueUserName;
    private String revenueDate;
}
