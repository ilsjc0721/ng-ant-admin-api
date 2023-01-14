package model.dto.other;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class ClassDateEntity implements Serializable {
    private Timestamp startDatetime;
    private Timestamp endDatetime;
}
