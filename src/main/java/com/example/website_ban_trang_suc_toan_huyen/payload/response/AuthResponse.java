package com.example.website_ban_trang_suc_toan_huyen.payload.response;


import com.example.website_ban_trang_suc_toan_huyen.entity.dto.AuthDTO;

public class AuthResponse extends BaseResponse<AuthDTO> {
    public AuthResponse(int status, String message, AuthDTO data) {
        super(status, message, data);
    }

    public AuthResponse() {
    }

    public AuthResponse(AuthDTO data) {
        super(data);
    }
}
