package com.example.website_ban_trang_suc_toan_huyen.restController;

import com.example.website_ban_trang_suc_toan_huyen.payload.request.LoginRequest;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.RegisterRequest;
import com.example.website_ban_trang_suc_toan_huyen.payload.response.LoginResponse;
import com.example.website_ban_trang_suc_toan_huyen.service.LoginService;
import com.example.website_ban_trang_suc_toan_huyen.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping("/api/v1")
@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest loginRequest){
        return loginService.login(loginRequest);
    }
    @PostMapping("/register")
    public LoginResponse register(@Valid @RequestBody RegisterRequest registerRequest){
        return loginService.register(registerRequest);
    }
}
