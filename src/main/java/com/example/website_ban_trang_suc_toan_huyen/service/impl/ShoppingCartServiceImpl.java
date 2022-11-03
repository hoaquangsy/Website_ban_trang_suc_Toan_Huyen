package com.example.website_ban_trang_suc_toan_huyen.service.impl;

import com.example.website_ban_trang_suc_toan_huyen.entity.dto.CartDetailDTO;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.CartDetailEntity;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.CartEntity;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.ProductEntity;
import com.example.website_ban_trang_suc_toan_huyen.exception.NotFoundException;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.CartRequest;
import com.example.website_ban_trang_suc_toan_huyen.repository.CartDetailRepository;
import com.example.website_ban_trang_suc_toan_huyen.repository.CartRepository;
import com.example.website_ban_trang_suc_toan_huyen.repository.ProductRepository;
import com.example.website_ban_trang_suc_toan_huyen.service.ShoppingCartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartDetailRepository cartDetailRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CartDetailDTO addToCart(CartRequest cartRequest) {
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
        cartDetailEntity.setId(UUID.randomUUID());
        cartDetailEntity.setCartId(cartId);

        Optional<ProductEntity> productEntity = productRepository.findById(cartRequest.getProductId());
        if(productEntity.isEmpty()){
            throw new NotFoundException(HttpStatus.NOT_FOUND.value(),"Product Not Found");
        }else {
            cartDetailEntity.setProductId(productEntity.get().getProductId());
        }

        CartDetailEntity cartDetailEn = cartDetailRepository.findByCartIdAndProductId(cartId, cartDetailEntity.getProductId());
        if(ObjectUtils.isEmpty(cartDetailEn)){
            cartDetailEntity.setAmount(cartRequest.getAmount());
        }else {
            cartDetailEntity.setAmount(cartRequest.getAmount() + cartDetailEn.getAmount());
        }

        CartDetailEntity cartDetail = this.cartDetailRepository.save(cartDetailEntity);

        return this.modelMapper.map(cartDetail, CartDetailDTO.class);
    }

    @Override
    public CartDetailDTO updateCart(UUID id, Integer amount) {
        CartDetailEntity cartDetailEntity = this.cartDetailRepository.findById(id).orElseThrow(() -> new NotFoundException(HttpStatus.NOT_FOUND.value(),"CartDetail Not Found"));

        if(amount<0){
            throw new NotFoundException(HttpStatus.BAD_REQUEST.value(),"CartDetail Not Found");
        }
        cartDetailEntity.setAmount(amount);

        return this.modelMapper.map(this.cartDetailRepository.save(cartDetailEntity), CartDetailDTO.class);
    }

    @Override
    public CartDetailDTO deleteCart(UUID id) {
        CartDetailEntity cartDetailEntity = this.cartDetailRepository.findById(id).orElseThrow(() -> new NotFoundException(HttpStatus.NOT_FOUND.value(),"CartDetail Not Found"));

        return this.modelMapper.map(cartDetailEntity, CartDetailDTO.class);
    }

    @Override
    public List<CartDetailDTO> getListCartDetailByCartId(UUID cartId) {
        List<CartDetailEntity> cartDetailEntityList = this.cartDetailRepository.findAllByCartId(cartId);

        if(cartDetailEntityList.isEmpty()){
            return null;
        }

        return cartDetailEntityList.stream().map(entity -> this.modelMapper.map(entity, CartDetailDTO.class)).collect(Collectors.toList());
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
