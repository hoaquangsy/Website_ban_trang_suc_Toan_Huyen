package com.example.website_ban_trang_suc_toan_huyen.payload.request;

import com.example.website_ban_trang_suc_toan_huyen.entity.entity.UserEntity;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class UserCustomerRequest {
    private String userName;

    private String fullName;

    private LocalDate birthday;

    private String password;

    private String confirmPassword;
    @NotNull(message = "Gender không để trống")
    private UserEntity.Gender gender;
    @NotNull(message = "Role không để trống")
    private UserEntity.Role role;
    @NotNull(message = "Số điện thoại không để trống")
    private String phoneNumber;

    private String cccd;
    @NotBlank(message = "Email không để trống")
    @Email
    private String email;
    @NotBlank(message = "Adress không để trống")
    private String address;

    private String imageUrl;

    private String note;

}
