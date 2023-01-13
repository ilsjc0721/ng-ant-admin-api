package model.dto.other;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@TableName("tsva_class")
public class ClassEntity implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String name;

    @TableField("start_datetime")
    private String startDatetime;

    @TableField("end_datetime")
    private String endDatetime;

    private BigDecimal hours;

    @TableField("course_id")
    private Integer courseId;

    @TableField("course_checked")
    private Boolean courseChecked;

    @TableField("update_user")
    private Integer updateUser;
}
