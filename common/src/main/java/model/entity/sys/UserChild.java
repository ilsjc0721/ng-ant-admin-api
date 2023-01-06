package model.entity.sys;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class UserChild {
    private Integer id;

    private Integer parentId;

    private String idNumber;

    private String userChName;

    private String userEnName;

    private Timestamp birth;

    private String gender;

    private String phone;

    private Boolean available;
}
