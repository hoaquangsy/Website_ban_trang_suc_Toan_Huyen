package com.example.website_ban_trang_suc_toan_huyen.repository;

import com.example.website_ban_trang_suc_toan_huyen.entity.entity.MaterialEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialRepository extends JpaRepository<MaterialEntity,Integer> {
    boolean existsByMaterialNameAndTypeAndStatus(String name, String type,Integer status);

    MaterialEntity findByMaterialNameAndTypeAndStatus(String name, String type, Integer status);
}
