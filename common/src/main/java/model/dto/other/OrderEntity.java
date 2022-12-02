package model.dto.other;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@TableName("ly_order")
public class OrderEntity implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("room_id")
    private Integer roomID;

    private String roomName;

    private String type;

    @TableField("in_date")
    private Timestamp inDate;

    @TableField("out_date")
    private Timestamp outDate;

    private String price;

    private String deposit;

    private String unPaid;

    @TableField("customer_id")
    private Integer customerId;

    private String customerName;

    private String status;

    @TableField("update_user")
    private Integer updateUser;

    @TableField("update_time")
    private Timestamp updateTime;
}
