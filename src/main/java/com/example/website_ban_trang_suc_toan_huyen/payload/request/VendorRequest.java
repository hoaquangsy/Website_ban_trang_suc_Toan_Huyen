package com.example.website_ban_trang_suc_toan_huyen.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class VendorRequest {
    private String name;
    private String address;
    private String bankName;
    private String bankNumber;
    private String email;
    private String phone;
    @JsonProperty("create_At")
    private Date createAt;
    @JsonProperty("create_By")
    private String createBy;
    @JsonProperty("last_Modified_At")
    private Date lastModifiedAt;
    @JsonProperty("last_Modified_By")
    private String lastModifiedBy;
}

