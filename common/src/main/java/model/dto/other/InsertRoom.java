package model.dto.other;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class InsertRoom {

    @NotBlank
    private String name;

    private String status;

    private Integer roomPrice1;

    private Integer roomPrice2;

    private Integer roomPrice3;

    private Integer roomPrice4;

    private Integer restPrice1;

    private Integer restPrice2;

    private Integer restPrice3;

    private Integer restPrice4;

    private Integer overtimePrice1;

    private Integer overtimePrice2;

    private Integer overtimePrice3;

    private Integer overtimePrice4;

    private Integer updateUser;
}
