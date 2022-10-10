package com.example.website_ban_trang_suc_toan_huyen.service;

import com.example.website_ban_trang_suc_toan_huyen.payload.request.LoginRequest;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.RegisterRequest;
import com.example.website_ban_trang_suc_toan_huyen.payload.response.LoginResponse;

public interface LoginService {
    LoginResponse  login(LoginRequest loginRequest);
    LoginResponse  register(RegisterRequest registerRequest);
}
