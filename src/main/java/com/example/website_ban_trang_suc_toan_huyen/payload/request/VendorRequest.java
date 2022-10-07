package com.example.website_ban_trang_suc_toan_huyen.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VendorRequest {
    @NotBlank(message = "Tên không để trống")
    private String name;

    @NotBlank(message = "Địa chỉ không để trống")
    private String address;

    @NotBlank(message = "Tên Ngân Hàng không để trống")
    private String bankName;

    @NotBlank(message = "Số Ngân hàng không để trống")
    private String bankNumber;

    @NotBlank(message = "Tên không để trống")
    @Email(message = "Email sai định dạng")
    private String email;
    @NotBlank(message = "Điện thoại không để trống")
    private String phone;
}

