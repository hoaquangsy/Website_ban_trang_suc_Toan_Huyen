package com.example.website_ban_trang_suc_toan_huyen.payload.request;

import lombok.Data;
import org.springframework.stereotype.Service;

@Data
public class LoginRequest {
    private String username;
    private String password;


}
