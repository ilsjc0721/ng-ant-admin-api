package model.entity.sys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
@Data
@TableName("sys_user")
public class SysUser implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("user_name")
    private String userName;

    @TableField("user_name_ch")
    private String userChName;

    @TableField("user_name_en")
    private String userEnName;

    private String password;

    @TableField("is_available")
    private Boolean available;

    @TableField("create_time")
    private Timestamp createTime;

    @TableField("update_time")
    private Timestamp updateTime;

    @TableField("address")
    private String address;

    @TableField("email")
    private String email;

    @TableField("last_login_time")
    private Timestamp lastLoginTime;

    @TableField("bank_code")
    private String bankCode;

    @TableField("bank_account")
    private String bankAccount;

    @TableField("emergency_contact_person")
    private String emergencyContactPerson;

    @TableField("emergency_contact_phone")
    private String emergencyContactPhone;

    @TableField("emergency_contact_relationship")
    private String emergencyContactRelationship;

    @TableField("picture_file_name")
    private String pictureFileName;
}
