package model.dto.other;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ClassConfirmRequest implements Serializable {
    private Integer id;
    private Boolean courseChecked;
    private List<ClassStudentRequest> studentList;
    private Integer updateUser;
    private String className;
}
