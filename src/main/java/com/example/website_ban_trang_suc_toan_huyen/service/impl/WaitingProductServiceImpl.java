package com.example.website_ban_trang_suc_toan_huyen.service.impl;

import com.example.website_ban_trang_suc_toan_huyen.dao.WaitingProductDAO;
import com.example.website_ban_trang_suc_toan_huyen.entity.dto.ProductDto;
import com.example.website_ban_trang_suc_toan_huyen.entity.dto.SizeDto;
import com.example.website_ban_trang_suc_toan_huyen.entity.dto.UserDTO;
import com.example.website_ban_trang_suc_toan_huyen.entity.dto.response.PageDTO;
import com.example.website_ban_trang_suc_toan_huyen.entity.dto.response.WaitingProductDTO;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.*;
import com.example.website_ban_trang_suc_toan_huyen.exception.NotFoundException;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.WaitingProductRequest;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.WaitingProductSearchRequest;
import com.example.website_ban_trang_suc_toan_huyen.repository.OrderDetailRepository;
import com.example.website_ban_trang_suc_toan_huyen.repository.ProductRepository;
import com.example.website_ban_trang_suc_toan_huyen.repository.ProductSizeRepository;
import com.example.website_ban_trang_suc_toan_huyen.repository.WaitingProductRepository;
import com.example.website_ban_trang_suc_toan_huyen.service.ProductService;
import com.example.website_ban_trang_suc_toan_huyen.service.SizeService;
import com.example.website_ban_trang_suc_toan_huyen.service.WaitingProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
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

    @Autowired
    private SizeService sizeService;

    @Autowired
    private ProductService productService;

    @Autowired
    private WaitingProductDAO waitingProductDAO;

    @Autowired
    private HttpSession session;

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
//        ProductEntity productEntity = productRepository.findByProductIdAndStatus(waitingProductRequest.getProductId(), ProductEntity.StatusEnum.ACTIVE.toString());
//        if (ObjectUtils.isEmpty(productEntity)) {
//            throw new NotFoundException(HttpStatus.NOT_FOUND.value(), "product not exist");
//        }
        ProductSizeEntity productSizeEntity = productSizeRepository.findByProductIdAndSizeId(waitingProductRequest.getProductId(), waitingProductRequest.getSizeId());
        if (ObjectUtils.isEmpty(productSizeEntity)) {
            throw new NotFoundException(HttpStatus.NOT_FOUND.value(), "size not exist");
        }
            WaitingProductEntity waitingProductEntityNew = new WaitingProductEntity();
            BeanUtils.copyProperties(waitingProductRequest, waitingProductEntityNew);
            waitingProductEntityNew.setId(UUID.randomUUID());
            return modelMapper.map(waitingProductRepository.save(waitingProductEntityNew), WaitingProductDTO.class);

    }

    @Override
    public WaitingProductDTO updateWaitingProduct(UUID id,WaitingProductRequest waitingProductRequest) {

        ProductSizeEntity productSizeEntity = productSizeRepository.findByProductIdAndSizeId(waitingProductRequest.getProductId(), waitingProductRequest.getSizeId());
        if (ObjectUtils.isEmpty(productSizeEntity)) {
            throw new NotFoundException(HttpStatus.NOT_FOUND.value(), "size not exist");
        }
        WaitingProductEntity waitingProductEntityNew = this.waitingProductRepository.findById(id).orElseThrow(() -> new NotFoundException(HttpStatus.NOT_FOUND.value(), "Sản phẩm gia công không tồn tại"));
        waitingProductEntityNew.setNote(waitingProductRequest.getNote());
        return modelMapper.map(waitingProductRepository.save(waitingProductEntityNew), WaitingProductDTO.class);

    }

    @Override
    public WaitingProductDTO sendProduct(UUID id) {
        WaitingProductEntity waitingProductEntity = waitingProductRepository.findById(id).orElseThrow(() -> new NotFoundException(HttpStatus.NOT_FOUND.value(), "Sản phẩm không tồn tại"));

        ProductSizeEntity productSizeEntity = productSizeRepository.findByProductIdAndSizeId(waitingProductEntity.getProductId(), waitingProductEntity.getSizeId());
        productSizeEntity.setQuantity(productSizeEntity.getQuantity() + waitingProductEntity.getQuantity());
        productSizeRepository.save(productSizeEntity);
        waitingProductRepository.delete(waitingProductEntity);
        return modelMapper.map(waitingProductEntity, WaitingProductDTO.class);
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

    @Override
    public PageDTO search(WaitingProductSearchRequest waitingProductSearchRequest) throws ParseException {
        List<WaitingProductEntity> waitingProductEntities =  this.waitingProductDAO.search(waitingProductSearchRequest);
        List<WaitingProductDTO> waitingProductDTOS = waitingProductEntities.stream().map(userEntity -> this.modelMapper.map(userEntity,WaitingProductDTO.class)).collect(Collectors.toList());
        waitingProductDTOS.forEach(waitingProductDTO -> {
            ProductDto productDto = this.productService.getProductById(waitingProductDTO.getProductId());
            waitingProductDTO.setProduct(productDto);
            SizeDto sizeDto = this.sizeService.getById(waitingProductDTO.getSizeId());
            waitingProductDTO.setSize(sizeDto);
        });
        Long count = this.waitingProductDAO.count(waitingProductSearchRequest);
        return new PageDTO(waitingProductDTOS,waitingProductSearchRequest.getPageIndex(),waitingProductSearchRequest.getPageSize(),count);
    }
}



