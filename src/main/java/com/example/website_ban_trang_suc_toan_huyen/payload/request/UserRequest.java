package com.example.website_ban_trang_suc_toan_huyen.payload.request;

import com.example.website_ban_trang_suc_toan_huyen.entity.entity.UserEntity;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class UserRequest {
    @NotBlank(message = "User không để trống")
    private String username;
    @NotBlank(message = "FullName không để trống")
    private String fullname;
    @NotNull(message = "birthday không để trống")
    private LocalDate birthday;
    @NotBlank(message = "Password không để trống")
    private String password;
    @NotBlank(message = "confirmPassword không để trống")
    private String confirmPassword;
    @NotNull(message = "Gender không để trống")
    private UserEntity.Gender gender;
    @NotNull(message = "Role không để trống")
    private UserEntity.Role role;
    @NotNull(message = "Role không để trống")
    private String phoneNumber;
    @NotBlank(message = "Email không để trống")
    @Email
    private String email;
    @NotBlank(message = "Adress không để trống")
    private String address;
    @NotBlank(message = "Avatar không để trống")
    private String imageUrl;
    @NotBlank(message = "Avatar không để trống")
    private String note;

}
