package com.example.website_ban_trang_suc_toan_huyen.service.impl;

import com.example.website_ban_trang_suc_toan_huyen.entity.dto.OrderDTO;
import com.example.website_ban_trang_suc_toan_huyen.entity.dto.ProductDto;
import com.example.website_ban_trang_suc_toan_huyen.entity.dto.ProductSizeDto;
import com.example.website_ban_trang_suc_toan_huyen.entity.dto.RefundDto;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.OrderEntity;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.ProductEntity;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.RefundEntity;
import com.example.website_ban_trang_suc_toan_huyen.exception.NotFoundException;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.RefundRequest;
import com.example.website_ban_trang_suc_toan_huyen.repository.OrderRepository;
import com.example.website_ban_trang_suc_toan_huyen.repository.ProductRepository;
import com.example.website_ban_trang_suc_toan_huyen.repository.RefundRepository;
import com.example.website_ban_trang_suc_toan_huyen.service.RefundService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class RefundServiceImpl implements RefundService {
    private final RefundRepository refundRepository;
    private final ModelMapper modelMapper;
    private final OrderRepository orderRepository;

    private final ProductRepository productRepository;

    public RefundServiceImpl(RefundRepository refundRepository, ModelMapper modelMapper, OrderRepository orderRepository, ProductRepository productRepository) {
        this.refundRepository = refundRepository;
        this.modelMapper = modelMapper;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Page<RefundDto> findAllRefund(int page, int pageSize) {
        Page<RefundEntity> refundEntityPage = refundRepository.findAll(PageRequest.of(page, pageSize));
        if (refundEntityPage.getTotalElements() > 0) {
            return refundEntityPage.map(RefundEntity -> modelMapper.map(RefundEntity, RefundDto.class));
        }
        throw new NotFoundException(HttpStatus.NOT_FOUND.value(), "Refund not exist");
    }

    @Override
    public RefundDto getRefundById(String id) {
        RefundEntity refund = refundRepository.findById(id).orElseThrow(()
                -> new NotFoundException(HttpStatus.NOT_FOUND.value(), "Cannot find refund with id = " + id));
        return modelMapper.map(refund, RefundDto.class);
    }

    @Override
    public HttpStatus deleteRefund(String id) {
        RefundEntity refund = refundRepository.findById(id).orElseThrow(()
                -> new NotFoundException(HttpStatus.NOT_FOUND.value(), "Cannot find refundId to delete"));
        refund.setStatus(false);
        refundRepository.save(refund);
        return HttpStatus.OK;
    }

    @Override
    public RefundDto createRefund(RefundRequest refundRequest) {
        if (productRepository.findById(refundRequest.getProductId()).isEmpty()) {
            throw new NotFoundException(HttpStatus.NOT_FOUND.value(), "product not exist");
        }
        if (orderRepository.findById(refundRequest.getOrderId()).isEmpty()){
            throw new NotFoundException(HttpStatus.NOT_FOUND.value(), "order not exist");
        }
        RefundEntity refund = modelMapper.map(refundRequest, RefundEntity.class);
        return modelMapper.map(refundRepository.save(refund), RefundDto.class);
    }

    @Override
    public RefundDto updateRefund(String refundId, RefundRequest refundRequest) {
        RefundEntity refundObj = refundRepository.findById(refundId).orElseThrow(()
                -> new NotFoundException(HttpStatus.NOT_FOUND.value(), "Cannot find refund with id = " + refundId));
        RefundEntity refund = modelMapper.map(refundRequest, RefundEntity.class);
        return  modelMapper.map(refundRepository.save(refund), RefundDto.class);
    }
}
