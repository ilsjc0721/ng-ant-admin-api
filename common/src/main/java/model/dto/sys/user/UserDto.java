package model.dto.sys.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserDto {
    @NotBlank
    private String userName;

    @NotBlank
    private String password;

    private String code;

    private String uuid = "";
}
