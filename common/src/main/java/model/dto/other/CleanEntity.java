package model.dto.other;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
@TableName("ly_clean")
public class CleanEntity implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("user_id")
    private Integer userId;

    @TableField("room_id")
    private Integer roomId;

    private String type;

    @TableField("start_datetime")
    private Timestamp startDatetime;

    @TableField("end_datetime")
    private Timestamp endDatetime;
}
