package model.dto.other;

import lombok.Data;

@Data
public class ApplyStudentEntity {
    private Integer id;
    private Integer applyId;
    private Integer studentId;
    private String studentName;
}
