package model.dto.other;

import lombok.Data;

import java.io.Serializable;

@Data
public class PriceEntity  implements Serializable {
    private String name;

    private Integer price;
}
