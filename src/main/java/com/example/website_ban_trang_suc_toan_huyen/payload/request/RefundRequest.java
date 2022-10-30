package com.example.website_ban_trang_suc_toan_huyen.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.UUID;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RefundRequest {
    @NotNull
    private UUID orderId;
    @NotNull
    private UUID productId;
    private String note;
    @JsonProperty("create_By")
    private String createBy;
    @JsonProperty("create_At")
    private Date createAt;
    @JsonProperty("last_Modified_At")
    private Date lastModifiedAt;
    @JsonProperty("last_Modified_By")
    private String lastModifiedBy;
    private String reason;
    private Boolean status;

}
