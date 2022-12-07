package model.dto.other;

import lombok.Data;

import java.util.Date;

@Data
public class CheckIn {
    private Integer id;
    private Integer roomId;
    private String type;
    private String payment;
    private Integer revenueUser;
    private Date revenueDate;
}
