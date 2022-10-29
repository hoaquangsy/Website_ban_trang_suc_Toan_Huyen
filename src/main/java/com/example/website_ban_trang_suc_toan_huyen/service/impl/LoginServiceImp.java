package com.example.website_ban_trang_suc_toan_huyen.service.impl;

import com.example.website_ban_trang_suc_toan_huyen.entity.entity.UserEntity;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.LoginRequest;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.RegisterRequest;
import com.example.website_ban_trang_suc_toan_huyen.payload.response.LoginResponse;
import com.example.website_ban_trang_suc_toan_huyen.repository.UserRepository;
import com.example.website_ban_trang_suc_toan_huyen.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImp implements LoginService {

    @Autowired
    UserRepository userRepository;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        LoginResponse response = new LoginResponse(loginRequest);

        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        UserEntity user = userRepository.finUserEntitybyUsername(username);

        if(user.getStatus().equals("Lock")){
            response.setMessage("Tài khoản của bạn đã bị khóa, vui lòng liên lạc với chúng tôi để mở khóa");
            return response;
        }
        if(user.getPassword().equals(password)){
            response.setMessage("Đăng nhập thành công");
            return response;
        }



        return response;
    }

    @Override
    public LoginResponse register(RegisterRequest registerRequest) {
        LoginResponse response = new LoginResponse(registerRequest);
        String username = registerRequest.getUsername();
        String password = registerRequest.getPassword();
        String confirmPassword = registerRequest.getPasswordConfirm();
        UserEntity user1 = userRepository.finUserEntitybyUsername(username);
        if(user1 != null){
            response.setMessage("Tài khoản đã tồn tại vui lòng tạo tài khoản khác");
            return  response;
        }
        if (!password.equals(confirmPassword)){
            response.setMessage("Mật khẩu xác nhận không trùng với mật khẩu");
            return  response;
        }
        if (password.length()<8){
            response.setMessage("mật khẩu trên 8 kí tự");
            return  response;
        }
        UserEntity user = new UserEntity();
        user.setUserName(username);
        user.setPassword(password);
        user.setGender(registerRequest.getGender());
        userRepository.save(user);
        response.setMessage("Thêm tài khoản thành công");
        return response;
    }
}
