package model.dto.other;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class SearchApplyDto {
    private Timestamp startDate;
    private Timestamp endDate;
    private Integer updateUser;
}
