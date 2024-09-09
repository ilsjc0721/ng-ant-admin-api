package model.dto.other;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Data
@TableName("tsva_deposit")
public class DepositEntity implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("user_id")
    private Integer userId;
    private String userName;

    @TableField("deposit_date")
    private Timestamp depositDate;

    private String type;

    private Integer deposit;

    @TableField("fee_id")
    private Integer feeId;

    @TableField("class_id")
    private Integer classId;
    private String className;

    private String memo;

    @TableField("update_time")
    private Timestamp updateTime;


}
