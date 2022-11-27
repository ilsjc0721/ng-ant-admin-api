package model.vo.sys;

import annotation.Excel;
import annotation.Excel.ColumnType;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * @program: fire_control
 * @description:
 * @author: fbl
 * @create: 2021-02-02 13:37
 **/
@Data
public class SelectUserVo {

    private Integer id;

    private String userName;

    private String userChName;

    private String userEnName;

    private Boolean available;

    private List<Integer> roleId;

    private List<String> roleName;

    private String email;

    private String address;

    private Timestamp lastLoginTime;

    private Timestamp createTime;
}
