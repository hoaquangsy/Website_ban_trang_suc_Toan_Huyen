package com.example.website_ban_trang_suc_toan_huyen.service.impl;

import com.example.website_ban_trang_suc_toan_huyen.entity.dto.CartDetailDTO;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.*;
import com.example.website_ban_trang_suc_toan_huyen.exception.NotFoundException;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.CartRequest;
import com.example.website_ban_trang_suc_toan_huyen.payload.response.GetCartResponse;
import com.example.website_ban_trang_suc_toan_huyen.repository.*;
import com.example.website_ban_trang_suc_toan_huyen.service.ShoppingCartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductSizeRepository productSizeRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartDetailRepository cartDetailRepository;
    @Autowired
    private SizeRepository sizeRepository;
    @Autowired
    private ProductImageRepository productImageRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CartDetailDTO addToCart(CartRequest cartRequest) {
//        CartDetailDTO response = new CartDetailDTO();

        // Find cartId
        Optional<CartEntity> cartEntityOptional = this.cartRepository.findByUserId(cartRequest.getUserId());
        CartEntity cartEntity = new CartEntity();
        UUID cartId = null;
        if(cartEntityOptional.isEmpty()){
            cartEntity.setId(UUID.randomUUID());
            cartEntity.setUserId(cartRequest.getUserId());

            cartId = cartEntity.getId();
            this.cartRepository.save(cartEntity);
        }
        else {
            cartId = cartEntityOptional.get().getId();
        }
        CartDetailEntity cartDetailEntity = new CartDetailEntity();


        // Check productId
        ProductEntity productEntity = productRepository.findByProductIdAndStatus(cartRequest.getProductId(), ProductEntity.StatusEnum.ACTIVE);
        System.out.println(productEntity.getStatus());
        if(ObjectUtils.isEmpty(productEntity)){
            throw new NotFoundException(HttpStatus.NOT_FOUND.value(),"Product Not Found");
        }else {
            cartDetailEntity.setProductId(productEntity.getProductId());
        }

        // Check số lượng của size sản phẩm còn lại
        ProductSizeEntity productSizeEntity = productSizeRepository.findByProductIdAndSizeId(productEntity.getProductId(), cartRequest.getSizeId());
        if (productSizeEntity.getQuantity()<=0){
            throw new NotFoundException(HttpStatus.BAD_REQUEST.value(),"Exceed the number of remaining products");
        }

        // Check product in cart
        CartDetailEntity cartDetailEn = cartDetailRepository.findByCartIdAndProductIdAndSizeId(cartId, cartRequest.getProductId(), cartRequest.getSizeId());
        if(ObjectUtils.isEmpty(cartDetailEn)){
            cartDetailEntity.setId(UUID.randomUUID());
            cartDetailEntity.setCartId(cartId);
            cartDetailEntity.setSizeId(cartRequest.getSizeId());
            if(cartRequest.getAmount()>productSizeEntity.getQuantity()){
                throw new NotFoundException(HttpStatus.BAD_REQUEST.value(),"Exceed the number of remaining products");
            }else {
                cartDetailEntity.setAmount(cartRequest.getAmount());
            }
        }else {
            if(cartRequest.getAmount()>productSizeEntity.getQuantity()){
                throw new NotFoundException(HttpStatus.BAD_REQUEST.value(),"Exceed the number of remaining products");
            }else {
                cartDetailEntity.setAmount(cartRequest.getAmount() + cartDetailEn.getAmount());
            }
            cartDetailEntity.setId(cartDetailEn.getId());
            cartDetailEntity.setCartId(cartId);
            cartDetailEntity.setSizeId(cartRequest.getSizeId());
        }

        SizeEntity sizeEntity = this.sizeRepository.findById(productSizeEntity.getSizeId()).get();
        CartDetailDTO.Size size = new CartDetailDTO.Size();
        size.setId(sizeEntity.getSizeId());
        size.setName(sizeEntity.getDescription());

        CartDetailEntity cartDetail = this.cartDetailRepository.save(cartDetailEntity);

        CartDetailDTO cartDetailDTO = this.modelMapper.map(cartDetail, CartDetailDTO.class);
        cartDetailDTO.setSize(size);

        return cartDetailDTO;
    }

    @Override
    public CartDetailDTO updateCart(UUID id, Integer amount) {
        CartDetailEntity cartDetailEntity = this.cartDetailRepository.findById(id).orElseThrow(() -> new NotFoundException(HttpStatus.NOT_FOUND.value(),"CartDetail Not Found"));

        if(amount<0){
            throw new NotFoundException(HttpStatus.BAD_REQUEST.value(),"amount > 0");
        }else if (amount==0){
            cartDetailRepository.delete(cartDetailEntity);
        }else {
            cartDetailEntity.setAmount(amount);
        }

        return this.modelMapper.map(this.cartDetailRepository.save(cartDetailEntity), CartDetailDTO.class);
    }

    @Override
    public CartDetailDTO deleteCart(UUID id) {
        CartDetailEntity cartDetailEntity = this.cartDetailRepository.findById(id).orElseThrow(() -> new NotFoundException(HttpStatus.NOT_FOUND.value(),"CartDetail Not Found"));
        cartDetailRepository.delete(cartDetailEntity);
        return this.modelMapper.map(cartDetailEntity, CartDetailDTO.class);
    }

    @Override
    public GetCartResponse getListCartDetailByCartId(UUID userId) {
        GetCartResponse response = new GetCartResponse();
        response.setUserId(userId);

        Optional<CartEntity> cartEntity = this.cartRepository.findByUserId(userId);
        response.setCartId(cartEntity.get().getId());

        List<CartDetailEntity> cartDetailEntityList = this.cartDetailRepository.findAllByCartId(cartEntity.get().getId());
        List<GetCartResponse.CartDetailResponse> cartDetailResponseList = new ArrayList<>();

        if(cartDetailEntityList.isEmpty()){
            return response;
        }

        for (CartDetailEntity cartDetail: cartDetailEntityList){
            GetCartResponse.CartDetailResponse cartDetailResponse = new GetCartResponse.CartDetailResponse();
            cartDetailResponse.setCartDetailId(cartDetail.getId());
            cartDetailResponse.setProductId(cartDetail.getProductId());

            ProductEntity productEntity = this.productRepository.findById(cartDetail.getProductId()).get();
            cartDetailResponse.setProductName(productEntity.getNameProduct());
            cartDetailResponse.setCode(productEntity.getCode());
            cartDetailResponse.setAmount(cartDetail.getAmount());

            ProductImageEntity productImageEntity = this.productImageRepository.findByProductId(cartDetail.getProductId()).get(0);
            cartDetailResponse.setImage(productImageEntity.getImageUrl());
            cartDetailResponse.setSizeId(cartDetail.getSizeId());

            SizeEntity sizeEntity = this.sizeRepository.findById(cartDetail.getSizeId()).get();
            cartDetailResponse.setSizeName(sizeEntity.getSize());

            ProductSizeEntity productSizeEntity = productSizeRepository.findByProductIdAndSizeId(productEntity.getProductId(),sizeEntity.getSizeId());
            cartDetailResponse.setPrice(productSizeEntity.getSalePrice());

            cartDetailResponseList.add(cartDetailResponse);
        }
        response.setCartDetailResponseList(cartDetailResponseList);

        return response;
    }

    @Override
    public Page<CartDetailDTO> getAllCart(int page, int pageSize) {
        Page<CartEntity> cartEntityPage = cartRepository.findAll(PageRequest.of(page, pageSize));
        if (cartEntityPage.getTotalElements() > 0) {
            return cartEntityPage.map(entity -> modelMapper.map(entity, CartDetailDTO.class));
        }
        throw new NotFoundException(HttpStatus.NOT_FOUND.value(), "Cart is empty");
    }
}
