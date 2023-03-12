package model.dto.sys.user;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class ResetUserPsdDto implements Serializable {
    @NotNull
    private Integer id;
}
