package com.example.website_ban_trang_suc_toan_huyen.service.impl;

import com.example.website_ban_trang_suc_toan_huyen.entity.entity.UserEntity;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.UserRequest;
import com.example.website_ban_trang_suc_toan_huyen.payload.response.UserResponse;
import com.example.website_ban_trang_suc_toan_huyen.repository.UserRepository;
import com.example.website_ban_trang_suc_toan_huyen.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserResponse addUser(UserRequest userRequest) {
        UserResponse response = new UserResponse();
        String username = userRequest.getUsername();
        String password = userRequest.getPassword();
        String passwordConfirm = userRequest.getConfirmPassword();
        String gender = userRequest.getGender();
        String role = userRequest.getRole();
        String adress= userRequest.getAddress();
        String phoneNumber = userRequest.getPhoneNumber();

        if(username.isEmpty()){
            response.setMessage("Tài khoản không được bỏ trống");
            return response;
        }
        UserEntity user1 = userRepository.finUserEntitybyUsername(username);
        if(user1 != null){
            response.setMessage("Tài khoản đã tồn tại vui lòng tạo tài khoản khác");
            return  response;
        }
        if(password.isEmpty()){
            response.setMessage("Mật khẩu không được bỏ trống");
            return response;
        }
        if(gender.isEmpty()){
            response.setMessage("Vui lòng chọn giới tính");
            return response;
        }
        if(role.isEmpty()){
            response.setMessage("Vui lòng chọn quyền");
            return response;
        }
        if(adress.isEmpty()){
            response.setMessage("Địa chỉ không được bỏ trống");
            return response;
        }
        if(phoneNumber.isEmpty()){
            response.setMessage("Số điện thoại không được bỏ trống");
            return response;
        }

        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setPassword(password);
        user.setGender(gender);
        user.setRole(role);
        user.setAddress(adress);
        user.setPhoneNumber(phoneNumber);
        Date date = new Date();
        user.setCreateAt(date);
        userRepository.save(user);
        response.setMessage("Thêm tài khoản thành công");
        return response;
    }

    @Override
    public UserResponse deleteUser(UserRequest userRequest) {
        UserResponse response = new UserResponse();
        String id = userRequest.getUserId();
        if (id.isEmpty()){
            response.setMessage("Chua chọn người dùng để xóa");
            return response;
        }
        userRepository.deleteById(id);
        response.setMessage("Xóa thành công");
        return response;
    }

    @Override
    public UserResponse getAllUser(UserRequest userRequest) {
        UserResponse response = new UserResponse();
        List<UserEntity> user = userRepository.findAllUsers();
        response.setUserEntity(user);
        response.setMessage("Lấy danh sách tài khoản thành công");
        return response;
    }

    @Override
    public UserResponse getUserByPhoneNumber(UserRequest userRequest) {
        UserResponse response = new UserResponse();
        List<UserEntity> user = userRepository.finUserEntityByPhoneNumber(userRequest.getPhoneNumber());
        response.setUserEntity(user);
        response.setMessage("Tìm kiếm tài khoản thành công");
        return response;
    }

    @Override
    public UserResponse updateUser(UserRequest userRequest){
        UserResponse response = new UserResponse();
        UserEntity user = new UserEntity();
        user.setUsername(userRequest.getUsername());
        user.setPassword(userRequest.getPassword());
        user.setGender(userRequest.getGender());
        user.setRole(userRequest.getRole());
        user.setAddress(userRequest.getAddress());
        user.setPhoneNumber(userRequest.getPhoneNumber());
        Date date = new Date();
        user.setCreateAt(date);
        userRepository.save(user);
        response.setMessage("Cập nhật tài khoản thành công");
        return response;
    }
}
