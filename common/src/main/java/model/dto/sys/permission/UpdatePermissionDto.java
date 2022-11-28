package model.dto.sys.permission;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 【请填写功能名称】对象 sys_permission
 *
 * @author fbl
 * @date 2022-03-31
 */
@Data
public class UpdatePermissionDto {
    @NotNull
    private Integer id;

    @NotBlank
    private String menuName;

    @NotBlank
    private String code;

    @NotNull
    private Integer fatherId;

    @NotNull
    private Integer orderNum;

    private String path;

    @NotBlank
    @Size(max = 1)
    private String menuType;

    private Boolean visible;

    private Boolean status;

    private String icon;

    private String alIcon;

    private Boolean newLinkFlag;
}
