package com.example.website_ban_trang_suc_toan_huyen.repository;

import com.example.website_ban_trang_suc_toan_huyen.dao.AccessoryDAO;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.AccessoryEntity;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.CategoryEntity;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, UUID> {
    @Query("select  c from EventEntity  c where c.eventId = :id and c.deleted = false")
    Optional<EventEntity> findId(UUID id);

    @Query("select  c from EventEntity  c where  c.deleted = false")
    List<EventEntity> getAll();
}
