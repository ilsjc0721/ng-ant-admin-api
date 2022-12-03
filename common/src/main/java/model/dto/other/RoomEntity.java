package model.dto.other;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@TableName("ly_room")
public class RoomEntity implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String name;

    private String status;

    @TableField("room_price1")
    private Integer roomPrice1;

    @TableField("room_price2")
    private Integer roomPrice2;

    @TableField("room_price3")
    private Integer roomPrice3;

    @TableField("room_price4")
    private Integer roomPrice4;

    @TableField("rest_price1")
    private Integer restPrice1;

    @TableField("rest_price2")
    private Integer restPrice2;

    @TableField("rest_price3")
    private Integer restPrice3;

    @TableField("rest_price4")
    private Integer restPrice4;

    @TableField("overtime_price1")
    private Integer overtimePrice1;

    @TableField("overtime_price2")
    private Integer overtimePrice2;

    @TableField("overtime_price3")
    private Integer overtimePrice3;

    @TableField("overtime_price4")
    private Integer overtimePrice4;

    @TableField("update_user")
    private Integer updateUser;

    @TableField("update_time")
    private Timestamp updateTime;
}
