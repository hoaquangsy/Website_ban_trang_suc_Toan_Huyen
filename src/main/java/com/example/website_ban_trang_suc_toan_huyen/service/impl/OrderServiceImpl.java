package com.example.website_ban_trang_suc_toan_huyen.service.impl;

import com.example.website_ban_trang_suc_toan_huyen.dao.OrderDao;
import com.example.website_ban_trang_suc_toan_huyen.entity.dto.EventDto;
import com.example.website_ban_trang_suc_toan_huyen.entity.dto.MaterialDto;
import com.example.website_ban_trang_suc_toan_huyen.entity.dto.OrderDTO;
import com.example.website_ban_trang_suc_toan_huyen.entity.dto.UserDTO;
import com.example.website_ban_trang_suc_toan_huyen.entity.dto.response.PageDTO;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.*;
import com.example.website_ban_trang_suc_toan_huyen.exception.BadRequestException;
import com.example.website_ban_trang_suc_toan_huyen.exception.NotFoundException;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.OrderRequest;
import com.example.website_ban_trang_suc_toan_huyen.repository.*;
import com.example.website_ban_trang_suc_toan_huyen.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductSizeRepository productSizeRepository;

    @Autowired
    private OrderDao orderDao;


    @Override
    @Transactional
    public OrderRequest saveOrder(OrderRequest orderRequest) {
        if (userRepository.findById(orderRequest.getUserId()).isEmpty()) {
            throw new NotFoundException(HttpStatus.NOT_FOUND.value(), "User not exist");
        }
        orderRequest.getOrderDetailList().forEach(orderDetailRq -> {
            Optional<ProductSizeEntity> productEntity = this.productSizeRepository.findByProductAndSize(orderDetailRq.getProductId(),orderDetailRq.getSizeId());
            if(productEntity.isEmpty()){
                throw  new NotFoundException(HttpStatus.NOT_FOUND.value(),"Sản phẩm không tồn tại");
            }
            if(productEntity.get().getQuantity() < orderDetailRq.getAmount()){
                throw new BadRequestException("Số lượng sản phẩm không đủ");
            }
        });
        OrderEntity orderRequestEntity = new OrderEntity();

        BeanUtils.copyProperties(orderRequest, orderRequestEntity);
        orderRequestEntity.setId(UUID.randomUUID());
        orderRequestEntity.setOrderCode(new  Date().getTime());
        OrderEntity order = orderRepository.save(orderRequestEntity);

        List<OrderDetailEntity> orderDetailEntityList = new ArrayList<>();
        orderRequest.getOrderDetailList().forEach(orderDetailRq -> {
            OrderDetailEntity orderDetailEntity = new OrderDetailEntity();
            BeanUtils.copyProperties(orderDetailRq, orderDetailEntity);
            orderDetailEntity.setId(UUID.randomUUID());
            orderDetailEntityList.add(orderDetailEntity);
        });
        orderDetailRepository.saveAll(orderDetailEntityList);
        orderDetailEntityList.forEach(orderDetailEntity -> {
            Optional<ProductSizeEntity> productSize = this.productSizeRepository.findByProductAndSize(orderDetailEntity.getProductId(),orderDetailEntity.getSizeId());
            ProductSizeEntity sizeEntity = productSize.orElseThrow(() -> new BadRequestException("Lỗi hệ thống"));
            sizeEntity.setQuantity(sizeEntity.getQuantity() - orderDetailEntity.getAmount());
            this.productSizeRepository.save(sizeEntity);
        });
        return orderRequest;
    }

    @Override
    public OrderDTO update(UUID idOrder, OrderEntity.StatusEnum status) {
        OrderEntity orderEntity = orderRepository.findById(idOrder).get();
        if (ObjectUtils.isEmpty(orderEntity)){
            throw new NotFoundException(HttpStatus.NOT_FOUND.value(), "Order not exist");
        }
        orderEntity.setStatus(status);
        return    modelMapper.map(orderRepository.save(orderEntity),OrderDTO.class);
    }

    @Override
    public OrderDTO findOrder(UUID idOrder){
        OrderEntity orderEntity = orderRepository.findById(idOrder).get();
        if (ObjectUtils.isEmpty(orderEntity)){
            throw new NotFoundException(HttpStatus.NOT_FOUND.value(), "Order not exist");
        }
        return    modelMapper.map(orderRepository.findById(idOrder).get(),OrderDTO.class);
    }

    @Override
    public Page<OrderDTO> getAllOrder(int page, int pageSize) {
        Page<OrderEntity> orderEntityPage = orderRepository.findAll(PageRequest.of(page, pageSize));
        if (orderEntityPage.getTotalElements() > 0) {
            return orderEntityPage.map(orderEntity -> modelMapper.map(orderEntity, OrderDTO.class));
        }
        throw new NotFoundException(HttpStatus.NOT_FOUND.value(), "Order not exist");
    }

    @Override
    public Page<OrderDTO> findByUser(int page, int pageSize, UUID userId) {
        Page<OrderEntity> orderEntityPage = orderRepository.findAllByUserId(userId,PageRequest.of(page, pageSize));
        if (orderEntityPage.getTotalElements() > 0) {
            return orderEntityPage.map(orderEntity -> modelMapper.map(orderEntity, OrderDTO.class));
        }
        throw new NotFoundException(HttpStatus.NOT_FOUND.value(), "Order not exist");
    }

    @Override
    public PageDTO search(Integer pageIndex, Integer pageSize, String keyword, OrderEntity.StatusEnum status, OrderEntity.PaymentMethod payMethod, OrderEntity.OrderType orderType, Instant startDate, Instant endDate, BigDecimal startPrice, BigDecimal endPrice,UUID userId, String sortBy) {
        Long count = this.orderDao.count(pageIndex,pageSize,keyword,status,payMethod,orderType,startDate,endDate,startPrice,endPrice,userId,sortBy);
       if(count == 0L){
           return PageDTO.empty();
       }
        List<OrderEntity> orderEntities = this.orderDao.search(pageIndex,pageSize,keyword,status,payMethod,orderType,startDate,endDate,startPrice,endPrice,userId,sortBy);
        List<OrderDTO> materialDtos = orderEntities.stream()
                .map(orderEntitie -> modelMapper.map(orderEntitie,OrderDTO.class)).collect(Collectors.toList());
       materialDtos.forEach(orderDTO -> {
           UserEntity user = this.userRepository.findUserEntitiesById(orderDTO.getUserId()).orElse(new UserEntity());
           System.out.println(user);
           orderDTO.setUser(this.modelMapper.map(user, UserDTO.class));
           EventEntity entity = this.eventRepository.findId(orderDTO.getEventId()).orElse(new EventEntity());
           orderDTO.setEvent(this.modelMapper.map(entity, EventDto.class));
       });
        return new PageDTO(materialDtos,pageIndex,pageSize,count);
    }

}
