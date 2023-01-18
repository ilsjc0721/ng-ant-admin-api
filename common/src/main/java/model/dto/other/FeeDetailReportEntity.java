package model.dto.other;

import lombok.Data;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class FeeDetailReportEntity {
    private Integer id;
    private Timestamp classDate;
    private BigDecimal classHours;
    private String className;
    private Integer classFee;
    private String classCoachName;
    private String classStudentName;
    private Integer feeId;
    private Integer classId;
}
