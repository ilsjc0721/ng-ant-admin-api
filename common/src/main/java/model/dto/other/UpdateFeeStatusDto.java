package model.dto.other;

import lombok.Data;

@Data
public class UpdateFeeStatusDto {
    private Integer userId;
    private String memo;
    private String status;
    private Integer id;
}
