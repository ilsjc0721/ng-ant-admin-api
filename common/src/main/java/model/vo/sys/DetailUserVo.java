package model.vo.sys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class DetailUserVo {
    private Integer id;

    private String userName;

    private String userChName;

    private String password;

    private Boolean available;

    private Timestamp createTime;

    private Timestamp updateTime;

    private String emergencyContactPerson;

    private String emergencyContactRelationship;

    private String emergencyContactPhone;

    private String address;

    private Timestamp lastLoginTime;

    private Timestamp startTime;

    private String telephone;

    private List<Integer> roleId;

    private List<String> roleName;
}
