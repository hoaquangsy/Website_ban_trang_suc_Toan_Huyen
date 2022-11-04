package com.example.website_ban_trang_suc_toan_huyen.repository;


import com.example.website_ban_trang_suc_toan_huyen.entity.entity.CartDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartDetailRepository extends JpaRepository<CartDetailEntity,UUID> {
    List<CartDetailEntity> findAllByCartId(UUID cartId);

    Optional<CartDetailEntity> findById(UUID cartId);

    CartDetailEntity findByCartIdAndProductId(UUID cartId, UUID productId);
}
