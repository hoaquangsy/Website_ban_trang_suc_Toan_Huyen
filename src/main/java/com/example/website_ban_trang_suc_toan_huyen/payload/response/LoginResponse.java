package com.example.website_ban_trang_suc_toan_huyen.payload.response;

import com.example.website_ban_trang_suc_toan_huyen.payload.request.LoginRequest;

public class LoginResponse {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LoginResponse(LoginRequest loginRequest) {
    }
}
