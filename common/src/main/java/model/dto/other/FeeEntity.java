package model.dto.other;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Data
@TableName("tsva_fee")
public class FeeEntity implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String period;

    private String type;

    @TableField("user_id")
    private Integer userID;

    private BigDecimal hours;

    private Integer amount;

    private String status;

    @TableField("update_user")
    private Integer updateUser;
}
