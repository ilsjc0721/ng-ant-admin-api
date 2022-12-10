package model.dto.other;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class ReportResponse implements Serializable {

    //common
    private String roomName;

    //order-reort
    private String customerName;
    private String orderType;
    private String payment;
    private String revenueType;
    private Integer revenue;
    private String revenueUserName;
    private String revenueDate;

    //clean-report
    private String cleanDate;
    private String cleanerName;
    private String startTime;
    private String endTime;
    private Integer cost;
    private String type;
}
