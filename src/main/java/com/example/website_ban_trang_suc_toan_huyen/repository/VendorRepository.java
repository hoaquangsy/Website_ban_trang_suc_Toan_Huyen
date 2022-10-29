package com.example.website_ban_trang_suc_toan_huyen.repository;


import com.example.website_ban_trang_suc_toan_huyen.dao.VendorDao;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.VendorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface VendorRepository extends JpaRepository<VendorEntity, UUID> , VendorDao {
    @Query("select  c from VendorEntity  c where c.vendorId = :id and c.deleted = false")
    Optional<VendorEntity> findByID(UUID id);

}
