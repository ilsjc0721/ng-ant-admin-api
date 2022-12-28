package model.dto.other;

import lombok.Data;

@Data
public class CourseFeeEntity {
    private Integer coachId;
    private String coachName;
    private String coachType;
    private Integer coachFee;
    private Integer tuitionFee;
}
