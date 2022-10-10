package com.example.website_ban_trang_suc_toan_huyen.repository;


import com.example.website_ban_trang_suc_toan_huyen.entity.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,String> {

    @Query(value = "SELECT * FROM UserEntity u WHERE and u.username = :username", nativeQuery = true)
    UserEntity finUserEntitybyUsername(@Param("username") String name);

    @Query(value = "SELECT u FROM UserEntity u")
    List<UserEntity> findAllUsers();

    @Query(value = "SELECT * FROM UserEntity u WHERE and u.phonenumber = :phonenumber", nativeQuery = true)
    List<UserEntity> finUserEntityByPhoneNumber(@Param("phonenumber") String phoneNumber);
}
