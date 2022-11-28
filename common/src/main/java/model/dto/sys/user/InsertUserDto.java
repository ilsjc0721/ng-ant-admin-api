package model.dto.sys.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;

/**
 * @program: fire_control
 * @description:
 * @author: fbl
 * @create: 2021-02-01 09:21
 **/
@Data
public class InsertUserDto {

    @NotBlank
    private String userName;

    @NotBlank
    private String password;

    private List<Integer> roleId;

    private String userChName;

    private String emergencyContactPerson;

    private String emergencyContactRelationship;

    private String emergencyContactPhone;

    private String address;

    private Timestamp startTime;

    private String telephone;
}
