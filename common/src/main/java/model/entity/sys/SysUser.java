package model.entity.sys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
@Data
@TableName("sys_user")
public class SysUser implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("user_name")
    private String userName;

    @TableField("user_name_ch")
    private String userChName;

    private String password;

    @TableField("is_available")
    private Boolean available;

    @TableField("create_time")
    private Timestamp createTime;

    @TableField("update_time")
    private Timestamp updateTime;

    @TableField("emergency_contact_person")
    private String emergencyContactPerson;

    @TableField("emergency_contact_relationship")
    private String emergencyContactRelationship;

    @TableField("emergency_contact_phone")
    private String emergencyContactPhone;

    private String address;

    @TableField("last_login_time")
    private Timestamp lastLoginTime;

    @TableField("start_time")
    private Timestamp startTime;

    private String telephone;
}
