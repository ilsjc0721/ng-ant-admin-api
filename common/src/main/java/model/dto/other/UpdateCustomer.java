package model.dto.other;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Data
public class UpdateCustomer {
    @NotNull
    private Integer id;

    @NotBlank
    private String name;

    @NotBlank
    private String idNumber;

    private String idType;

    private String adr;

    private String phone;

    private String other1;

    private String other2;

    private String memo;

    private String emName;

    private String emPhone;

    private String emRelation;

    private Integer updateUser;

    private String carNumber;
}
