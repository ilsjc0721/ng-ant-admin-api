package model.dto.other;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class ClassResponse implements Serializable {
    private Integer id;
    private String name;
    private String startDatetime;
    private String endDatetime;
    private Integer hours;
    private Integer limit;
    private String coach;
    private String student;
    private String updateUserName;
    private Timestamp updateTime;
}
