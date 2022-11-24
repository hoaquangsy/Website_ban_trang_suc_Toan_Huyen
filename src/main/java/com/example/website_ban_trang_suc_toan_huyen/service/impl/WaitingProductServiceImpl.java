package com.example.website_ban_trang_suc_toan_huyen.service.impl;

import com.example.website_ban_trang_suc_toan_huyen.entity.dto.response.WaitingProductDTO;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.OrderDetailEntity;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.ProductEntity;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.ProductSizeEntity;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.WaitingProductEntity;
import com.example.website_ban_trang_suc_toan_huyen.exception.NotFoundException;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.WaitingProductRequest;
import com.example.website_ban_trang_suc_toan_huyen.repository.OrderDetailRepository;
import com.example.website_ban_trang_suc_toan_huyen.repository.ProductRepository;
import com.example.website_ban_trang_suc_toan_huyen.repository.ProductSizeRepository;
import com.example.website_ban_trang_suc_toan_huyen.repository.WaitingProductRepository;
import com.example.website_ban_trang_suc_toan_huyen.service.WaitingProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class WaitingProductServiceImpl implements WaitingProductService {
    @Autowired
    private WaitingProductRepository waitingProductRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductSizeRepository productSizeRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<WaitingProductDTO> findAllByOrderId(UUID orderId) {
        List<OrderDetailEntity> orderDetailEntityList = orderDetailRepository.findByOrderId(orderId);
        if (orderDetailEntityList.isEmpty()) {
            throw new NotFoundException(HttpStatus.NOT_FOUND.value(), "order not exist");
        }
        List<UUID> productIds = orderDetailEntityList.stream().map(OrderDetailEntity::getProductId).collect(Collectors.toList());
        List<WaitingProductEntity> waitingProductEntities = waitingProductRepository.findAllByIdIn(productIds);
        List<WaitingProductDTO> waitingProductDTOS = new ArrayList<>();
        waitingProductEntities.forEach(waitingProductEntity -> {
            WaitingProductDTO waitingProductDTO = new WaitingProductDTO();
            BeanUtils.copyProperties(waitingProductEntity, waitingProductDTO);
            waitingProductDTOS.add(waitingProductDTO);
        });
        return waitingProductDTOS;
    }

    @Override
    public WaitingProductDTO createWaitingProduct(WaitingProductRequest waitingProductRequest) {
        ProductEntity productEntity = productRepository.findByProductIdAndStatus(waitingProductRequest.getProductId(), ProductEntity.StatusEnum.ACTIVE.toString());
        if (ObjectUtils.isEmpty(productEntity)) {
            throw new NotFoundException(HttpStatus.NOT_FOUND.value(), "product not exist");
        }
        ProductSizeEntity productSizeEntity = productSizeRepository.findByProductIdAndSizeId(waitingProductRequest.getProductId(), waitingProductRequest.getSizeId());
        if (ObjectUtils.isEmpty(productSizeEntity)) {
            throw new NotFoundException(HttpStatus.NOT_FOUND.value(), "size not exist");
        }
        WaitingProductEntity waitingProductEntity = waitingProductRepository.findAllByProductIdAndSizeId(waitingProductRequest.getProductId(), waitingProductRequest.getSizeId());
        if (ObjectUtils.isEmpty(waitingProductEntity)) {
            WaitingProductEntity waitingProductEntityNew = new WaitingProductEntity();
            BeanUtils.copyProperties(waitingProductRequest, waitingProductEntityNew);
            return modelMapper.map(waitingProductRepository.save(waitingProductEntityNew), WaitingProductDTO.class);
        } else {
            waitingProductEntity.setQuantity(waitingProductEntity.getQuantity() + waitingProductRequest.getQuantity());
            return modelMapper.map(waitingProductRepository.save(waitingProductEntity), WaitingProductDTO.class);
        }
    }

    @Override
    public WaitingProductDTO sendProduct(WaitingProductRequest waitingProductRequest) {
        WaitingProductEntity waitingProductEntity = waitingProductRepository.findAllByProductIdAndSizeId(waitingProductRequest.getProductId(), waitingProductRequest.getSizeId());
        if (waitingProductRequest.getQuantity() > waitingProductEntity.getQuantity()) {
            throw new NotFoundException(HttpStatus.NOT_FOUND.value(), "Số lượng không hợp lệ");
        }
        ProductSizeEntity productSizeEntity = productSizeRepository.findByProductIdAndSizeId(waitingProductRequest.getProductId(), waitingProductRequest.getSizeId());
        productSizeEntity.setQuantity(productSizeEntity.getQuantity() + waitingProductRequest.getQuantity());
        productSizeRepository.save(productSizeEntity);
        waitingProductEntity.setQuantity(waitingProductEntity.getQuantity() - waitingProductRequest.getQuantity());
        return modelMapper.map(waitingProductRepository.save(waitingProductEntity), WaitingProductDTO.class);
    }

    @Override
    public List<WaitingProductDTO> findAll() {
        List<WaitingProductEntity> waitingProductEntities = waitingProductRepository.findAll();
        List<WaitingProductDTO> waitingProductDTOS = new ArrayList<>();
        waitingProductEntities.forEach(waitingProductEntity -> {
            WaitingProductDTO waitingProductDTO = new WaitingProductDTO();
            BeanUtils.copyProperties(waitingProductEntity, waitingProductDTO);
            waitingProductDTOS.add(waitingProductDTO);
        });
        return waitingProductDTOS;
    }
}



