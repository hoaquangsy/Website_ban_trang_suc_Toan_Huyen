package com.example.website_ban_trang_suc_toan_huyen.service.impl;

import com.example.website_ban_trang_suc_toan_huyen.entity.dto.OrderDTO;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.OrderDetailEntity;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.OrderEntity;
import com.example.website_ban_trang_suc_toan_huyen.exception.NotFoundException;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.OrderRequest;
import com.example.website_ban_trang_suc_toan_huyen.repository.OrderDetailRepository;
import com.example.website_ban_trang_suc_toan_huyen.repository.OrderRepository;
import com.example.website_ban_trang_suc_toan_huyen.repository.UserRepository;
import com.example.website_ban_trang_suc_toan_huyen.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Transactional
    public OrderRequest saveOrder(OrderRequest orderRequest) {
        if (userRepository.findById(orderRequest.getUserId()).isEmpty()) {
            throw new NotFoundException(HttpStatus.NOT_FOUND.value(), "User not exist");
        }
        OrderEntity orderRequestEntity = new OrderEntity();

        BeanUtils.copyProperties(orderRequest, orderRequestEntity);
        OrderEntity order = orderRepository.save(orderRequestEntity);

        List<OrderDetailEntity> orderDetailEntityList = new ArrayList<>();
        orderRequest.getOrderDetailList().stream().forEach(orderDetailRq -> {
            OrderDetailEntity orderDetailEntity = new OrderDetailEntity();
            BeanUtils.copyProperties(orderDetailRq, orderDetailEntity);
            orderDetailEntity.setOrderId(order.getId());
            orderDetailEntityList.add(orderDetailEntity);
        });
        orderDetailRepository.saveAll(orderDetailEntityList);
        return orderRequest;
    }

    public OrderDTO update(UUID idOrder, Integer status) {
        OrderEntity orderEntity = orderRepository.findById(idOrder).get();
        if (ObjectUtils.isEmpty(orderEntity)){
            throw new NotFoundException(HttpStatus.NOT_FOUND.value(), "Order not exist");
        }
        orderEntity.setStatus(status);
        return    modelMapper.map(orderRepository.save(orderEntity),OrderDTO.class);
    }
}
