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
@TableName("tsva_apply")
public class ApplyEntity implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("course_id")
    private Integer courseId;
    private String courseName;

    @TableField("apply_date")
    private Timestamp applyDate;

    @TableField("start_time")
    private Timestamp startTime;

    @TableField("end_time")
    private Timestamp endTime;

    private Integer hours;

    @TableField("update_user")
    private Integer updateUser;
    private String updateUserName;

    @TableField("update_time")
    private Timestamp updateTime;

    private List<ApplyStudentEntity> applyStudent;
    private String applyStudentShow;
}
