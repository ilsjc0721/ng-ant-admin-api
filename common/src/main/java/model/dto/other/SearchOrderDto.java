package model.dto.other;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class SearchOrderDto {
    private Integer id;
    private Integer roomId;
    private Timestamp inDate;
    private Integer customerId;
}
