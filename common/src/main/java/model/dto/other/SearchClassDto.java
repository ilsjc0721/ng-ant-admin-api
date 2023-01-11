package model.dto.other;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class SearchClassDto {
    private String name;
    private Timestamp startDate;
    private Timestamp endDate;
    private Integer coach;
    private Integer student;
    private Boolean notConfirm;
}
