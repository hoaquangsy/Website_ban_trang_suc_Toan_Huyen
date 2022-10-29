package com.example.website_ban_trang_suc_toan_huyen.service;

import com.example.website_ban_trang_suc_toan_huyen.entity.dto.UserDTO;
import com.example.website_ban_trang_suc_toan_huyen.entity.dto.response.PageDTO;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.UserEntity;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.UserRequest;
import com.example.website_ban_trang_suc_toan_huyen.payload.response.UserResponse;

import java.util.List;
import java.util.UUID;

public interface UserService {
     UserDTO addUser(UserRequest userRequest);
     UserDTO deleteUser(UUID id);
     UserResponse getAllUser(UserRequest userRequest);
     UserResponse getUserByPhoneNumber(UserRequest userRequest);
     UserDTO updateUser(UUID id,UserRequest userRequest);

     UserDTO lock(UUID uuid);

     UserDTO unlock(UUID uuid);
     PageDTO search(String keyword, UserEntity.Role role, Integer pageNumber, Integer pageSize, String sortBy,Boolean status);

     List<UserDTO> getCustomer();
}
