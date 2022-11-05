package com.example.website_ban_trang_suc_toan_huyen.repository;


import com.example.website_ban_trang_suc_toan_huyen.entity.entity.CategoryPropertyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CategoryPropertyRepository extends JpaRepository<CategoryPropertyEntity, UUID> {
    @Query("select c.categoryPropertyId from CategoryPropertyEntity c where c.categoryId = :id")
    List<UUID> findCategoryPropertyEntitiesByCategoryId(UUID id);

    List<CategoryPropertyEntity> findByCategoryId(UUID uuid);
}
