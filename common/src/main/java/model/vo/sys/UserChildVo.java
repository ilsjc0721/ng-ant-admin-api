package model.vo.sys;

import lombok.Data;

@Data
public class UserChildVo {
    private Integer id;

    private Integer parentId;

    private String idNumber;

    private String userChName;

    private String userEnName;

    private String birth;

    private String gender;

    private String phone;

    private Boolean available;
}
