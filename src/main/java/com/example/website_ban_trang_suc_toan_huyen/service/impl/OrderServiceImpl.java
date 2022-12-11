package com.example.website_ban_trang_suc_toan_huyen.service.impl;

import com.example.website_ban_trang_suc_toan_huyen.dao.OrderDao;
import com.example.website_ban_trang_suc_toan_huyen.entity.dto.*;
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

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
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

    @Autowired
    private SizeRepository sizeRepository;

    @Autowired
    private ProductImageRepository productImageRepository;


    @Autowired
    private HttpSession session;


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
            if(productEntity.get().getQuantity() < orderDetailRq.getQuantity()){
                throw new BadRequestException("Số lượng sản phẩm không đủ");
            }
        });
        OrderEntity orderRequestEntity = new OrderEntity();

        BeanUtils.copyProperties(orderRequest, orderRequestEntity);
        orderRequestEntity.setId(UUID.randomUUID());
        orderRequestEntity.setOrderCode(new  Date().getTime());
        orderRequestEntity.setIsRepurchase(Boolean.FALSE);
        OrderEntity order = orderRepository.save(orderRequestEntity);

        List<OrderDetailEntity> orderDetailEntityList = new ArrayList<>();
        orderRequest.getOrderDetailList().forEach(orderDetailRq -> {
            OrderDetailEntity orderDetailEntity = new OrderDetailEntity();
            BeanUtils.copyProperties(orderDetailRq, orderDetailEntity);
            orderDetailEntity.setId(UUID.randomUUID());
            orderDetailEntity.setOrderId(order.getId());
            orderDetailEntityList.add(orderDetailEntity);
        });
        orderDetailRepository.saveAll(orderDetailEntityList);
        orderDetailEntityList.forEach(orderDetailEntity -> {
            Optional<ProductSizeEntity> productSize = this.productSizeRepository.findByProductAndSize(orderDetailEntity.getProductId(),orderDetailEntity.getSizeId());
            ProductSizeEntity sizeEntity = productSize.orElseThrow(() -> new BadRequestException("Lỗi hệ thống"));
            sizeEntity.setQuantity(sizeEntity.getQuantity() - orderDetailEntity.getQuantity());
            this.productSizeRepository.save(sizeEntity);
        });
        return orderRequest;
    }

    @Override
    @Transactional
    public OrderRequest updateWaitOrder(UUID idOrder,OrderRequest orderRequest) {
        if (userRepository.findById(orderRequest.getUserId()).isEmpty()) {
            throw new NotFoundException(HttpStatus.NOT_FOUND.value(), "User not exist");
        }
        orderRequest.getOrderDetailList().forEach(orderDetailRq -> {
            Optional<ProductSizeEntity> productEntity = this.productSizeRepository.findByProductAndSize(orderDetailRq.getProductId(),orderDetailRq.getSizeId());
            if(productEntity.isEmpty()){
                throw  new NotFoundException(HttpStatus.NOT_FOUND.value(),"Sản phẩm không tồn tại");
            }
            if(productEntity.get().getQuantity() < orderDetailRq.getQuantity()){
                throw new BadRequestException("Số lượng sản phẩm không đủ");
            }
        });
        OrderEntity orderRequestEntity = orderRepository.findById(idOrder).orElseThrow(() -> new NotFoundException(HttpStatus.NOT_FOUND.value(), "Order không tồn tại"));
        BeanUtils.copyProperties(orderRequest, orderRequestEntity);
        orderRepository.save(orderRequestEntity);
        List<UUID> uuids = orderDetailRepository.findByOrderId(orderRequestEntity.getId()).stream().map(OrderDetailEntity::getId).collect(Collectors.toList());
       List<OrderDetailEntity> orderDetailEntities = orderDetailRepository.findByOrderId(orderRequestEntity.getId());
       List<ProductSizeEntity> productSizeEntities = new ArrayList<>();
       orderDetailEntities.forEach(orderDetailEntity -> {
           Optional<ProductSizeEntity> sizeEntity = this.productSizeRepository.findByProductAndSize(orderDetailEntity.getProductId(),orderDetailEntity.getSizeId());
           if(sizeEntity.isPresent()){
               ProductSizeEntity sizeEntity1 =   sizeEntity.get();
               sizeEntity1.setQuantity(sizeEntity.get().getQuantity() + orderDetailEntity.getQuantity());
               productSizeEntities.add(sizeEntity1);
           }
           this.productSizeRepository.saveAll(productSizeEntities);
       });
        orderDetailRepository.deleteAllById(uuids);
        List<OrderDetailEntity> orderDetailEntityList = new ArrayList<>();
        orderRequest.getOrderDetailList().forEach(orderDetailRq -> {
            OrderDetailEntity orderDetailEntity = new OrderDetailEntity();
            BeanUtils.copyProperties(orderDetailRq, orderDetailEntity);
            orderDetailEntity.setId(UUID.randomUUID());
            orderDetailEntity.setOrderId(orderRequestEntity.getId());
            orderDetailEntityList.add(orderDetailEntity);
        });
        orderDetailRepository.saveAll(orderDetailEntityList);
        orderDetailEntityList.forEach(orderDetailEntity -> {
            Optional<ProductSizeEntity> productSize = this.productSizeRepository.findByProductAndSize(orderDetailEntity.getProductId(),orderDetailEntity.getSizeId());
            ProductSizeEntity sizeEntity = productSize.orElseThrow(() -> new BadRequestException("Lỗi hệ thống"));
            sizeEntity.setQuantity(sizeEntity.getQuantity() - orderDetailEntity.getQuantity());
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
        OrderEntity orderEntity = orderRepository.findById(idOrder).orElseThrow(() -> new NotFoundException(HttpStatus.NOT_FOUND.value(), "Order không tồn tại"));
       OrderDTO orderDTO =  modelMapper.map(orderEntity,OrderDTO.class);
       List<ProductOrderDto> productOrderDtos = new ArrayList<>();
   List<OrderDetailEntity> orderDetailEntities = this.orderDetailRepository.findByOrderId(orderDTO.getId());
   orderDetailEntities.forEach(orderDetailEntity -> {
       ProductOrderDto productOrderDto = new ProductOrderDto();
       productOrderDto.setProductId(orderDetailEntity.getProductId());
       productOrderDto.setSizeId(orderDetailEntity.getSizeId());
       productOrderDto.setId(orderDetailEntity.getId());
       ProductEntity productEntity = this.productRepository.findID(orderDetailEntity.getProductId()).get();
       SizeEntity entity = this.sizeRepository.getSizeEntitiesBy(orderDetailEntity.getSizeId()).get();
       productOrderDto.setSize(entity.getSize());
       productOrderDto.setNameProduct(productEntity.getNameProduct());
       productOrderDto.setQuantity(orderDetailEntity.getQuantity());
       productOrderDto.setPrice(orderDetailEntity.getPrice());
       productOrderDto.setSalary(productEntity.getSalary());
       List<ProductImageEntity> productImageEntity = this.productImageRepository.findByProductId(orderDetailEntity.getProductId());
       productOrderDto.setImageUrl(productImageEntity.stream().map(ProductImageEntity::getImageUrl).collect(Collectors.toList()));
      productOrderDtos.add(productOrderDto);
   });
       orderDTO.setOrderDetailDTOList(productOrderDtos);
        return orderDTO;
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
    public PageDTO search(Integer pageIndex, Integer pageSize, String keyword, OrderEntity.StatusEnum status, OrderEntity.PaymentMethod payMethod, OrderEntity.OrderType orderType, String startDate, String endDate, BigDecimal startPrice, BigDecimal endPrice, UUID userId, String sortBy) throws ParseException {
        Long count = this.orderDao.count(pageIndex,pageSize,keyword,status,payMethod,orderType,startDate,endDate,startPrice,endPrice,userId,sortBy,Boolean.FALSE);
       if(count == 0L){
           return PageDTO.empty();
       }
        List<OrderEntity> orderEntities = this.orderDao.search(pageIndex,pageSize,keyword,status,payMethod,orderType,startDate,endDate,startPrice,endPrice,userId,sortBy,Boolean.FALSE);
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
