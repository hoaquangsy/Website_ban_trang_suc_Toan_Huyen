package com.example.website_ban_trang_suc_toan_huyen.repository;


import com.example.website_ban_trang_suc_toan_huyen.dao.AccessoryDAO;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.AccessoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AccessoryRepository extends JpaRepository<AccessoryEntity, UUID>, AccessoryDAO {

    @Query("select c from AccessoryEntity c where c.deleted = false")
    List<AccessoryEntity> getAll();
}
