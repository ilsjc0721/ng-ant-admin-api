package model.vo.sys;

import lombok.Data;

import java.util.List;

/**
 * @program: fire_control
 * @description:
 * @author: fbl
 * @create: 2021-02-03 10:36
 **/
@Data
public class DetailUserVo {
    private Integer id;

    private String userName;

    private String userChName;

    private String userEnName;

    private String email;

    private String address;

    private Boolean available;

    private List<String> roleName;

    private List<Integer> roleId;

    private String bankCode;

    private String bankAccount;

    private String createTime;

    private String emergencyContactPerson;

    private String emergencyContactPhone;

    private String emergencyContactRelationship;

    private List<UserChildVo> userChildVoList;

    private String pictureFileName;
}
