package com.example.website_ban_trang_suc_toan_huyen.service;

import com.example.website_ban_trang_suc_toan_huyen.payload.request.UserRequest;
import com.example.website_ban_trang_suc_toan_huyen.payload.response.UserResponse;

public interface UserService {
    public UserResponse addUser(UserRequest userRequest);
    public UserResponse deleteUser(UserRequest userRequest);
    public UserResponse getAllUser(UserRequest userRequest);
    public UserResponse getUserByPhoneNumber(UserRequest userRequest);
    public UserResponse updateUser(UserRequest userRequest);
}
