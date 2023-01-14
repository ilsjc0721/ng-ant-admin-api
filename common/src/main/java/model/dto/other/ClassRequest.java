package model.dto.other;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class ClassRequest implements Serializable {
    private Integer id;

    private String startDatetime;

    private String endDatetime;

    private BigDecimal hours;

    private Integer courseId;

    private Boolean courseChecked;

    private Integer updateUser;

    private List<Integer> childId;

    private List<Integer> coachId;

    private List<ClassDateEntity> classDateList;

}
