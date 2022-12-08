package model.dto.other;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class SearchReportDto {
    private Timestamp startDate;
    private Timestamp endDate;
    private String orderType;
    private String payment;
    private String revenueType;
    private Integer revenueUser;
}
