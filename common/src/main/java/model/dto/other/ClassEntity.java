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
@TableName("tsva_class")
public class ClassEntity implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("location")
    private String location;

    @TableField("start_datetime")
    private Timestamp startDatetime;

    @TableField("end_datetime")
    private Timestamp endDatetime;

    private BigDecimal hours;

    @TableField("course_id")
    private Integer courseId;

    @TableField("course_checked")
    private Boolean courseChecked;

    @TableField("update_user")
    private Integer updateUser;

    @TableField("classroom_fee")
    private Integer classroomFee;

    @TableField("course_type")
    private String courseType;
}
