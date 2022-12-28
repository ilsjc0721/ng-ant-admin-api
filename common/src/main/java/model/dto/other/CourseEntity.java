package model.dto.other;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@TableName("tsva_course")
public class CourseEntity implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String name;
    private String courseType;
    private String status;
    private Integer limit;
    private Boolean apply;

    @TableField("update_user")
    private Integer updateUser;

    private String updateUserName;

    @TableField("update_time")
    private Timestamp updateTime;
}
