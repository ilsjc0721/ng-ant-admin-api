package model.dto.other;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@TableName("tsva_classroom_fee")
public class ClassroomFeeEntity implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String period;

    private String type;

    @TableField("class_id")
    private Integer classId;

    @TableField("class_date")
    private Timestamp classDate;

    @TableField("class_location")
    private String classLocation;

    @TableField("class_hours")
    private BigDecimal classHours;

    @TableField("class_name")
    private String className;

    @TableField("classroom_fee")
    private Integer classroomFee;

    @TableField("class_coach_name")
    private String classCoachName;

    @TableField("class_student_name")
    private String classStudentName;
}
