package com.example.website_ban_trang_suc_toan_huyen.restController;


import com.example.website_ban_trang_suc_toan_huyen.entity.dto.response.PageDTO;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.UserEntity;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.UserCustomerRequest;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.UserRequest;
import com.example.website_ban_trang_suc_toan_huyen.payload.response.SampleResponse;
import com.example.website_ban_trang_suc_toan_huyen.payload.response.UserResponse;
import com.example.website_ban_trang_suc_toan_huyen.service.UserService;
import com.example.website_ban_trang_suc_toan_huyen.service.impl.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserServiceImp userServiceImp;

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@Validated @RequestBody UserRequest userRequest){
        return ResponseEntity.ok(SampleResponse.success(userService.addUser(userRequest)));
    }
    @PostMapping("/addCustomer")
    public ResponseEntity<?> addUserCustomer(@Validated @RequestBody UserCustomerRequest userRequest){
        return ResponseEntity.ok(SampleResponse.success(userService.addUserCustomer(userRequest)));
    }

    @PostMapping("{id}/delete")
    public ResponseEntity<?> deleteUser(@PathVariable("id") UUID id){
        return ResponseEntity.ok(SampleResponse.success(this.userService.deleteUser(id)));
    }
    @PostMapping("{id}/update")
    public ResponseEntity<?> updateUserResponse(@PathVariable("id") UUID id, @Validated @RequestBody UserRequest userRequest){
        return ResponseEntity.ok(SampleResponse.success(this.userService.updateUser(id,userRequest)));
    }
    @PostMapping("{id}/lock")
    public ResponseEntity<?> lockUser(@PathVariable("id") UUID id){
        return ResponseEntity.ok(SampleResponse.success(this.userService.lock(id)));
    }
    @PostMapping("{id}/unlock")
    public ResponseEntity<?> unlockUser(@PathVariable("id") UUID id){
        return ResponseEntity.ok(SampleResponse.success(this.userService.unlock(id)));
    }

    @PostMapping("/findUser")
    public UserResponse findUserByPhoneNumber(UserRequest userRequest){
        return userService.getUserByPhoneNumber(userRequest);
    }

    @GetMapping("/findByUsername")
    public ResponseEntity<?> find(@RequestParam("userName") String username){
        return ResponseEntity.ok(SampleResponse.success(userService.getUserByUserName(username)));
    }
    @PostMapping("/getAll")
    public UserResponse getAllUser(UserRequest userRequest){
        return userService.getAllUser(userRequest);
    }
    @GetMapping("/customer")
    public ResponseEntity<?> getEmployee(){
        return ResponseEntity.ok(SampleResponse.success(userService.getCustomer()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") UUID id){
        return ResponseEntity.ok(SampleResponse.success(userServiceImp.getById(id)));
    }

    @GetMapping("")
    public PageDTO search(@RequestParam(value = "pageIndex",required = false,defaultValue = "1") Integer pageNumber,
                          @RequestParam(value = "pageSize",required = false,defaultValue = "10") Integer pageSize,
                          @RequestParam(value = "keyword",required = false,defaultValue = "") String keyword,
                          @RequestParam(value = "sortBy",required = false) String sortBy,
                          @RequestParam(value = "role",required = false) UserEntity.Role role,
                          @RequestParam(value = "status",required = false) Boolean status){
        return userService.search(keyword,role,pageNumber,pageSize,sortBy,status);
    }
}
