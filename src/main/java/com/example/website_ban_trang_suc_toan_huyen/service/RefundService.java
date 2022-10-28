package com.example.website_ban_trang_suc_toan_huyen.service;

import com.example.website_ban_trang_suc_toan_huyen.entity.dto.ProductDto;
import com.example.website_ban_trang_suc_toan_huyen.entity.dto.RefundDto;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.OrderRequest;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.ProductRequest;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.RefundRequest;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

import java.util.UUID;

public interface RefundService  {
    Page<RefundDto> findAllRefund(int page, int pageSize);
    RefundDto getRefundById(String refundId);

    HttpStatus deleteRefund(String refundId);

    RefundDto createRefund(RefundRequest refundRequest);

    RefundDto updateRefund(String refundId , RefundRequest refundRequest);

}
