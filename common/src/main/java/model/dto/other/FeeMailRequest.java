package model.dto.other;

import lombok.Data;

@Data
public class FeeMailRequest {
    private String period;
    private Integer feeId;
    private String subject;
}
