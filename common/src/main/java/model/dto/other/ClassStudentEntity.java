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
@TableName("tsva_class_student")
public class ClassStudentEntity implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("class_id")
    private Integer classId;

    @TableField("student_id")
    private Integer studentId;

    @TableField("start_datetime")
    private Timestamp startDatetime;

    @TableField("end_datetime")
    private Timestamp endDatetime;

    private BigDecimal hours;

    @TableField("tuition_fee")
    private Integer tuitionFee;

    @TableField("tuition_total")
    private Integer tuitionTotal;
}
