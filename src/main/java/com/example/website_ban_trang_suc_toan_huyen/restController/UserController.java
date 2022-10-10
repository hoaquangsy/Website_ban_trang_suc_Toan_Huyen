package com.example.website_ban_trang_suc_toan_huyen.restController;


import com.example.website_ban_trang_suc_toan_huyen.payload.request.UserRequest;
import com.example.website_ban_trang_suc_toan_huyen.payload.response.UserResponse;
import com.example.website_ban_trang_suc_toan_huyen.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")

public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public UserResponse addUser(UserRequest userRequest){
        return userService.addUser(userRequest);
    }
    @PostMapping("/delete")
    public UserResponse deleteUser(UserRequest userRequest){
        return userService.deleteUser(userRequest);
    }
    @PostMapping("/update")
    public UserResponse updateUserResponse(UserRequest userRequest){
        return userService.updateUser(userRequest);
    }

    @PostMapping("/findUser")
    public UserResponse findUserByPhoneNumber(UserRequest userRequest){
        return userService.getUserByPhoneNumber(userRequest);
    }
    @PostMapping("/getAll")
    public UserResponse getAllUser(UserRequest userRequest){
        return userService.getAllUser(userRequest);
    }
}
