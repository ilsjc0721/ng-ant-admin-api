package model.dto.other;

import lombok.Data;

@Data
public class SearchFeeReportDto {
    private String period;
    private String type;
    private Integer userId;
    private String status;
    private Integer feeId;
}
