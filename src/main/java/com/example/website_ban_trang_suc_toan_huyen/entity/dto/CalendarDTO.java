package com.example.website_ban_trang_suc_toan_huyen.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalendarDTO {

    @NotBlank
    @NotNull
    private UUID id;
    @NotBlank
    @NotNull
    private UUID orderId;
    @NotBlank
    @NotNull
    private Date time;
    @NotBlank
    @NotNull
    private Integer status;
    @NotBlank
    @NotNull
    private String userName;
    @NotBlank
    @NotNull
    private String phoneNumber;
    @NotBlank
    @NotNull
    private BigDecimal customerMoney;
    @NotBlank
    @NotNull
    private BigDecimal arrears;
}
