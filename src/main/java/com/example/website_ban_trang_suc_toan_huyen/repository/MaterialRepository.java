package com.example.website_ban_trang_suc_toan_huyen.repository;

import com.example.website_ban_trang_suc_toan_huyen.dao.MaterialDao;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.MaterialEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MaterialRepository extends JpaRepository<MaterialEntity, UUID> {
    boolean existsByMaterialNameAndTypeAndStatus(String name, MaterialEntity.MaterialType type, MaterialEntity.StatusEnum status);

    @Query("select  m from MaterialEntity m where m.materialName = :name and m.type = :type  and m.materialId <> :id")
    Optional<MaterialEntity> checkMaterialDuplicate(
            @Param("name") String name,@Param("type") MaterialEntity.MaterialType type
           ,@Param("id") UUID id);
    MaterialEntity findByMaterialNameAndTypeAndStatus(String name, String type, Integer status);
}
