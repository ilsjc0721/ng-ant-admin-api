package model.dto.sys.user;

import lombok.Data;
import model.entity.sys.UserChild;

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

    private boolean available;

    private String userChName;

    private String userEnName;

    private String email;

    private String address;

    private String bankCode;

    private String bankAccount;

    private String emergencyContactPerson;

    private String emergencyContactPhone;

    private String emergencyContactRelationship;

    private List<UserChild> userChildVoList;

    private Timestamp joinTime;

    private String emailAccount;

    private String emailKey;
}
