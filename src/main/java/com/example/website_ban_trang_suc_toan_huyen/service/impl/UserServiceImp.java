package com.example.website_ban_trang_suc_toan_huyen.service.impl;

import com.example.website_ban_trang_suc_toan_huyen.dao.UserDao;
import com.example.website_ban_trang_suc_toan_huyen.entity.dto.UserDTO;
import com.example.website_ban_trang_suc_toan_huyen.entity.dto.response.PageDTO;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.UserEntity;
import com.example.website_ban_trang_suc_toan_huyen.exception.BadRequestException;
import com.example.website_ban_trang_suc_toan_huyen.exception.NotFoundException;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.UserRequest;
import com.example.website_ban_trang_suc_toan_huyen.payload.response.UserResponse;
import com.example.website_ban_trang_suc_toan_huyen.repository.UserRepository;
import com.example.website_ban_trang_suc_toan_huyen.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    UserDao userDao;

    @Override
    public UserDTO addUser(UserRequest userRequest) {
        if(!userRequest.getPassword().equals(userRequest.getConfirmPassword())){
            throw new BadRequestException("Confirm password không chính xác");
        }
        if(this.userRepository.findUserEntitiesByEmail(userRequest.getEmail()).isPresent()){
            throw new BadRequestException("Email đã tồn tại");
        }
        if(this.userRepository.finUserEntitybyUsername(userRequest.getUserName()) != null) {
            throw new BadRequestException("Username đã tồn tại");
        }
        UserEntity user = this.modelMapper.map(userRequest,UserEntity.class);
        if(user.getRole() == UserEntity.Role.EMPLOYEE){
          List<UserEntity> userLast = userRepository.getUserEmployee();
          if(!CollectionUtils.isEmpty(userLast) && userLast.size() > 0){
              userLast =  userLast.stream().sorted((o1, o2) -> o2.getMaNV().compareTo(o1.getMaNV())).collect(Collectors.toList());
            UserEntity user1 =   userLast.get(0);
            int maNv = Integer.parseInt(user1.getMaNV().substring(2));
            user.setMaNV("NV"+(maNv+1));
          }else{
              user.setMaNV("NV1");
          }
        }
        user.setUserId(UUID.randomUUID());
        user.setDeleted(Boolean.FALSE);
        user.setStatus(Boolean.FALSE);
        return this.modelMapper.map(this.userRepository.save(user),UserDTO.class);
    }

    @Override
    public UserDTO deleteUser(UUID id) {
        UserEntity user = this.userRepository.findUserEntitiesById(id).orElseThrow(() -> new NotFoundException(HttpStatus.NOT_FOUND.value(), "User not found"));
        user.setDeleted(Boolean.TRUE);
        return this.modelMapper.map(this.userRepository.save(user),UserDTO.class);

    }

    private UserDTO getById(UUID id){
        UserEntity user = this.userRepository.findUserEntitiesById(id).orElseThrow(() -> new NotFoundException(HttpStatus.NOT_FOUND.value(), "User not found"));
        return this.modelMapper.map(user,UserDTO.class);
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
    public UserDTO updateUser(UUID id ,UserRequest userRequest){
        if(!userRequest.getPassword().equals(userRequest.getConfirmPassword())){
            throw new BadRequestException("Confirm password không chính xác");
        }
        if(this.userRepository.findUserEntitiesByEmail(userRequest.getEmail(),id).isPresent()){
            throw new BadRequestException("Email đã tồn tại");
        }
        UserEntity user = this.userRepository.findUserEntitiesById(id).orElseThrow(()-> new NotFoundException(HttpStatus.NOT_FOUND.value(), "User not found"));
        user.setGender(userRequest.getGender());
        user.setRole(userRequest.getRole());
        user.setAddress(userRequest.getAddress());
        user.setPhoneNumber(userRequest.getPhoneNumber());
        user.setCccd(userRequest.getCccd());
        user.setFullName(userRequest.getFullName());
        user.setBirthday(userRequest.getBirthday());
        user.setImageUrl(userRequest.getImageUrl() == null || userRequest.getImageUrl().equals("") ? user.getImageUrl() : userRequest.getImageUrl());
        user.setEmail(userRequest.getEmail());
        user.setNote(userRequest.getNote());
        return this.modelMapper.map(  userRepository.save(user),UserDTO.class);
    }

    @Override
    public UserDTO lock(UUID uuid) {
        UserEntity user  = this.userRepository.findUserEntitiesById(uuid).orElseThrow(()-> new NotFoundException(HttpStatus.NOT_FOUND.value(), "User not found"));
        user.setStatus(Boolean.TRUE);
        return this.modelMapper.map(this.userRepository.save(user),UserDTO.class);
    }

    @Override
    public UserDTO unlock(UUID uuid) {
        UserEntity user  = this.userRepository.findUserEntitiesById(uuid).orElseThrow(()-> new NotFoundException(HttpStatus.NOT_FOUND.value(), "User not found"));
        user.setStatus(Boolean.FALSE);
        return this.modelMapper.map(this.userRepository.save(user),UserDTO.class);
    }

    @Override
    public PageDTO search(String keyword, UserEntity.Role role, Integer pageNumber, Integer pageSize, String sortBy,Boolean status) {
        List<UserEntity> userEntities =  this.userDao.search(keyword,role,pageNumber,pageSize,sortBy,status);
        List<UserDTO> userDTOS = userEntities.stream().map(userEntity -> this.modelMapper.map(userEntity,UserDTO.class)).collect(Collectors.toList());
        Long count = this.userDao.count(keyword,role,pageNumber,pageSize,sortBy,status);
        return new PageDTO(userDTOS,pageNumber,pageSize,count);
    }

    @Override
    public List<UserDTO> getCustomer() {
        List<UserEntity> userEntities =  this.userRepository.findCustomer();
        return userEntities.stream().map(userEntity -> this.modelMapper.map(userEntity,UserDTO.class)).collect(Collectors.toList());
    }
}
