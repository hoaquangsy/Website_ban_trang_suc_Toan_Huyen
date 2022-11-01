package com.example.website_ban_trang_suc_toan_huyen.payload.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Data
//@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ExchangeRequest {
    @NotNull
    private UUID orderId;
    @NotNull
    private List<UUID> productId;
    private String note;
    private String reason;
    private Boolean status;

}
