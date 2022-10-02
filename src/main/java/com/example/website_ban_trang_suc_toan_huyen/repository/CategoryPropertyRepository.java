package com.example.website_ban_trang_suc_toan_huyen.repository;


import com.example.website_ban_trang_suc_toan_huyen.entity.entity.AccessoryEntity;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.CategoryPropertyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryPropertyRepository extends JpaRepository<CategoryPropertyEntity,String> {
}
