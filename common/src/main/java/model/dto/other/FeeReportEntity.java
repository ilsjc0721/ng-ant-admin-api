package model.dto.other;

import lombok.Data;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Data
public class FeeReportEntity {
    private Integer id;
    private String period;
    private String type;
    private Integer userID;
    private String userName;
    private BigDecimal hours;
    private Integer amount;
    private String status;
    private Integer updateUser;
    private String updateUserName;
    private Timestamp updateTime;
    private boolean expand;
    private String memo;
    private String email;
    private List<FeeDetailReportEntity> feeDetailReport;
}
