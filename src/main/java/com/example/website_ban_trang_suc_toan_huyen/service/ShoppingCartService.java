package com.example.website_ban_trang_suc_toan_huyen.service;

import com.example.website_ban_trang_suc_toan_huyen.entity.dto.CartDetailDTO;
//import com.example.website_ban_trang_suc_toan_huyen.payload.request.CartRequest;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.CartRequest;
import com.example.website_ban_trang_suc_toan_huyen.payload.response.GetCartResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.List;
import java.util.UUID;
@Service
@SessionScope
public interface ShoppingCartService {
    CartDetailDTO addToCart(CartRequest cartRequest);

    CartDetailDTO updateCart(UUID id, Integer amount);

    CartDetailDTO deleteCart(UUID id);

    GetCartResponse getListCartDetailByCartId(UUID userId);

    Page<CartDetailDTO> getAllCart(int page, int pageSize);

    CartDetailDTO deleteCartDetailByUserId(UUID id);
}