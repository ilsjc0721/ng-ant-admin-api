package model.dto.other;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Data
public class ClassResponse implements Serializable {
    private Integer id;
    private String name;
    private String startDatetime;
    private String endDatetime;
    private BigDecimal hours;
    private Integer limit;
    private String coach;
    private String parent;
    private String student;
    private String updateUserName;
    private Timestamp updateTime;
    private Integer courseId;
    private List<Integer> coachId;
    private List<Integer> studentId;
    private String courseType;
    private Boolean courseChecked;
    private String classStatus;
    private Integer coachFeeStatus;
    private Integer studentFeeStatus;
}
