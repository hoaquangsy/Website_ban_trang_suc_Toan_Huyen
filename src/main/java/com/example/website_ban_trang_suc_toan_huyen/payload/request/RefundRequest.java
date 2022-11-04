package com.example.website_ban_trang_suc_toan_huyen.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.UUID;

@Data
public class RefundRequest {
    @NotNull
    private UUID orderId;

    @NotNull
    private UUID productId;

    private String note;

    @NotNull
    private String reason;

    @NotNull
    private Boolean status;

}
