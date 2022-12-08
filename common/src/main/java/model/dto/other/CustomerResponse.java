package model.dto.other;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class CustomerResponse implements Serializable {
    private Integer id;

    private String name;

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

    private String updateUserName;

    private Timestamp updateTime;

    private String carNumber;
}
