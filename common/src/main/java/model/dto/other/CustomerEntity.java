package model.dto.other;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@TableName("ly_customer")
public class CustomerEntity implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String name;

    @TableField("id_number")
    private String idNumber;

    @TableField("id_type")
    private String idType;

    private String adr;

    private String phone;

    private String other1;

    private String other2;

    private String memo;

    @TableField("em_name")
    private String emName;

    @TableField("em_phone")
    private String emPhone;

    @TableField("em_relation")
    private String emRelation;

    @TableField("update_user")
    private Integer updateUser;

    @TableField("update_time")
    private Timestamp updateTime;

    @TableField("car_number")
    private String carNumber;

}
