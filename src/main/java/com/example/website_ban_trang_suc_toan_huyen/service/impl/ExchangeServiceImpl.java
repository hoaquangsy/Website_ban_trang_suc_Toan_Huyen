package com.example.website_ban_trang_suc_toan_huyen.service.impl;

import com.example.website_ban_trang_suc_toan_huyen.entity.entity.ExchangeEntity;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.OrderDetailEntity;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.OrderEntity;
import com.example.website_ban_trang_suc_toan_huyen.exception.NotFoundException;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.ExchangeRequest;
import com.example.website_ban_trang_suc_toan_huyen.repository.ExchangeRepository;
import com.example.website_ban_trang_suc_toan_huyen.repository.OrderDetailRepository;
import com.example.website_ban_trang_suc_toan_huyen.repository.OrderRepository;
import com.example.website_ban_trang_suc_toan_huyen.service.ExchangeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ExchangeServiceImpl implements ExchangeService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private ExchangeRepository exchangeRepository;


    @Override
    public List<ExchangeEntity> createExchange(ExchangeRequest request) {
        OrderEntity order = orderRepository.findByIdAndStatus(request.getOrderId(), OrderEntity.StatusEnum.DA_GIAO.name());
        if (ObjectUtils.isEmpty(order)) {
            throw new NotFoundException(HttpStatus.NOT_FOUND.value(), "Order không tồn tại");
        }
        Instant start = order.getCreateAt();
//        Instant start = Instant.parse("2022-10-30T10:33:50.63Z");
        Long time = start.until(Instant.now(), ChronoUnit.MILLIS);
        if (time > 172800000) {
            throw new NotFoundException(HttpStatus.NOT_FOUND.value(), "Order đã quá hạn");
        }
        List<OrderDetailEntity> orderDetailEntityList = orderDetailRepository.
                findByOrderIdAndProductIdIn(request.getOrderId(), request.getProductId());
        if (request.getProductId().size() != orderDetailEntityList.size()) {
            throw new NotFoundException(HttpStatus.NOT_FOUND.value(), "Trong order không có sản phẩm này");
        }
        List<ExchangeEntity> exchangeEntities = exchangeRepository.
                findByOrderIdAndProductIdIn(request.getOrderId(), request.getProductId());
        if (!exchangeEntities.isEmpty()) {
            throw new NotFoundException(HttpStatus.NOT_FOUND.value(), "Sản phẩm này đã đổi 1 lần");
        }
        ExchangeEntity exchangeEntity = exchangeRepository.findByOrder(request.getOrderId()).get(0);
        var ref = new Object() {
            Integer timeEx = 0;
        };
        if (!ObjectUtils.isEmpty(exchangeEntity)) {
            ref.timeEx = exchangeEntity.getTime() + 1;
        }
        List<ExchangeEntity> exchangeEntities1 = new ArrayList<>();
        request.getProductId().forEach(productId -> {
            UUID uuid = UUID.randomUUID();
            ExchangeEntity exchange = new ExchangeEntity();
            BeanUtils.copyProperties(request, exchange);
            exchange.setTime(ref.timeEx);
            exchange.setId(uuid);
            exchange.setProductId(productId);
            exchangeEntities1.add(exchange);
        });
        return exchangeRepository.saveAll(exchangeEntities1);
    }
    @Override
    public List<ExchangeEntity> getAllExchangeByTime(UUID orderId){
        ExchangeEntity exchangeEntity = exchangeRepository.findByOrder(orderId).get(0);
        List<ExchangeEntity> exchangeEntities =exchangeRepository.findByOrderIdAndTime(orderId,exchangeEntity.getTime());
        return exchangeEntities;
    }
    @Override
    public List<ExchangeEntity> getAllExchangeByOrder(UUID orderId){
        List<ExchangeEntity> exchangeEntityList = exchangeRepository.findByOrder(orderId);
        return exchangeEntityList;
    }
}
