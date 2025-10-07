package model.dto.other;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class ClassStudentRequest implements Serializable {
    private Integer id;

    private Integer classId;

    private Integer studentId;

    private Timestamp startDatetime;

    private Timestamp endDatetime;

    private BigDecimal hours;

    private Integer tuitionFee;

    private String studentName;
}
