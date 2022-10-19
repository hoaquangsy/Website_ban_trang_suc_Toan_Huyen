package com.example.website_ban_trang_suc_toan_huyen.repository;


import com.example.website_ban_trang_suc_toan_huyen.entity.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    @Query(value = "SELECT * FROM user u WHERE  u.username = :username", nativeQuery = true)
    UserEntity finUserEntitybyUsername(@Param("username") String name);

    @Query(value = "SELECT u FROM UserEntity u")
    List<UserEntity> findAllUsers();

    @Query(value = "SELECT * FROM user u WHERE  u.phonenumber = :phonenumber", nativeQuery = true)
    List<UserEntity> finUserEntityByPhoneNumber(@Param("phonenumber") String phoneNumber);

    @Query(value = "SELECT u FROM UserEntity u WHERE  u.email = :email")
    Optional<UserEntity> findUserEntitiesByEmail(String email);

    @Query(value = "SELECT u FROM UserEntity u WHERE u.userId = :id and u.deleted = false")
    Optional<UserEntity> findUserEntitiesById(UUID id);


    @Query(value = "SELECT u FROM UserEntity u WHERE  u.email = :email and u.userId <> :id")
    Optional<UserEntity> findUserEntitiesByEmail(String email,UUID id);

    @Query(value = "SELECT u FROM UserEntity u WHERE  u.userName = :username and u.userId <> :id")
    Optional<UserEntity> findUserEntitiesbyUserName(String username,UUID id);
}
