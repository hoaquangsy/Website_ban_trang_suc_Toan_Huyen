package com.example.website_ban_trang_suc_toan_huyen.repository;

import com.example.website_ban_trang_suc_toan_huyen.entity.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MessageRepository  extends JpaRepository<MessageEntity, UUID> {
    @Query("SELECT m FROM MessageEntity m where m=?1  order by m.createAt ASC ")
    List<MessageEntity> findByUserId(UUID userId);
    @Query("SELECT DISTINCT m.userId  FROM MessageEntity m order by m.createAt ASC ")
    List<MessageEntity> findByUserIdUnique();
}
