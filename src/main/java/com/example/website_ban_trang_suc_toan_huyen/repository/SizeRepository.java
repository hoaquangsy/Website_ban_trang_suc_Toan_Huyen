package com.example.website_ban_trang_suc_toan_huyen.repository;


import com.example.website_ban_trang_suc_toan_huyen.entity.entity.AccessoryEntity;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.SizeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SizeRepository extends JpaRepository<SizeEntity,UUID> {

    @Query("Select s from SizeEntity  s where s.deleted = false")
    List<SizeEntity> getAllBy();

    @Query("Select s from SizeEntity  s where s.deleted = false and s.sizId = :id")
    Optional<SizeEntity> getSizeEntitiesBy(UUID id);


}
