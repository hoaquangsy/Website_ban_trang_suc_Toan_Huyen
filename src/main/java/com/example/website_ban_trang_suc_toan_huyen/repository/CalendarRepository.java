package com.example.website_ban_trang_suc_toan_huyen.repository;

import com.example.website_ban_trang_suc_toan_huyen.entity.entity.CalendarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CalendarRepository extends JpaRepository<CalendarEntity, UUID> {
}
