package model.dto.other;

import lombok.Data;

@Data
public class ClassStudentResponse {
    private Integer id;
    private Integer keyId;
    private String name;
    private String nameCh;
    private Integer tuitionFee;
    private String parentName;
    private String parentNameCh;
}
