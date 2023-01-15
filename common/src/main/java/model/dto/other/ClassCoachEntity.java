package model.dto.other;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("tsva_class_coach")
public class ClassCoachEntity implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("class_id")
    private Integer classId;

    @TableField("coach_id")
    private Integer coachId;

    @TableField("coach_fee")
    private Integer coachFee;

    @TableField("coach_type")
    private String coachType;

    @TableField("coach_total")
    private Integer coachTotal;
}
