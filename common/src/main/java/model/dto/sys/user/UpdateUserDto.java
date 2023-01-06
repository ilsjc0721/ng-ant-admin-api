package model.dto.sys.user;

import lombok.Data;
import model.entity.sys.UserChild;
import model.vo.sys.UserChildVo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @program: fire_control
 * @description:
 * @author: fbl
 * @create: 2021-02-02 08:38
 **/
@Data
public class UpdateUserDto implements Serializable {

    @NotNull
    private Integer id;

    @NotBlank
    private String userName;

    @NotNull
    private Boolean available;

    private List<Integer> roleId;

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
}
