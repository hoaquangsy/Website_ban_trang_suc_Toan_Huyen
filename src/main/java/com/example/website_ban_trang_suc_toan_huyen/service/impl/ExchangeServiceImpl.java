package com.example.website_ban_trang_suc_toan_huyen.service.impl;

import com.example.website_ban_trang_suc_toan_huyen.dao.ExchangeDAO;
import com.example.website_ban_trang_suc_toan_huyen.entity.dto.ExchangeDTO;
import com.example.website_ban_trang_suc_toan_huyen.entity.dto.OrderDTO;
import com.example.website_ban_trang_suc_toan_huyen.entity.dto.ProductOrderDto;
import com.example.website_ban_trang_suc_toan_huyen.entity.dto.UserDTO;
import com.example.website_ban_trang_suc_toan_huyen.entity.dto.response.PageDTO;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.*;
import com.example.website_ban_trang_suc_toan_huyen.exception.BadRequestException;
import com.example.website_ban_trang_suc_toan_huyen.exception.NotFoundException;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.ExchangeRequest;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.ExchangeSearchRequest;
import com.example.website_ban_trang_suc_toan_huyen.repository.*;
import com.example.website_ban_trang_suc_toan_huyen.service.ExchangeService;
import org.hibernate.criterion.Order;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ExchangeServiceImpl implements ExchangeService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private ExchangeRepository exchangeRepository;

    @Autowired
    private ExchangeDetailRepository exchangeDetailRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ExchangeDAO exchangeDAO;
    @Autowired
    private SizeRepository sizeRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    private ProductSizeRepository productSizeRepository;

    @Autowired
    private WaitingProductRepository waitingProductRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private HttpSession session;
    @Override
    public PageDTO search(ExchangeSearchRequest exchangeSearchRequest) throws ParseException {
        Long count = this.exchangeDAO.count(exchangeSearchRequest);
        if(count == 0L){
            return PageDTO.empty();
        }
        List<ExchangeEntity> exchangeEntities = this.exchangeDAO.search(exchangeSearchRequest);
        List<ExchangeDTO> exchangeDTOS  = new ArrayList<>();
        exchangeEntities.forEach(exchangeEntity -> {
            ExchangeDTO exchangeDTO = this.modelMapper.map(exchangeEntity,ExchangeDTO.class);
            exchangeDTO.setOrderDTO(this.modelMapper.map(this.orderRepository.findById(exchangeDTO.getOrderId()).get(), OrderDTO.class));
            exchangeDTO.getOrderDTO().setUser(this.modelMapper.map(this.userRepository.findById(exchangeDTO.getOrderDTO().getUserId()).orElse(new UserEntity()),UserDTO.class));
            exchangeDTOS.add(exchangeDTO);
        });
        return new PageDTO(exchangeDTOS,exchangeSearchRequest.getPageIndex(),exchangeSearchRequest.getPageSize(),count);
    }

    @Override
    @Transactional
    public ExchangeDTO createExchange(ExchangeRequest request) {
        OrderEntity order = orderRepository.findByIdAndStatus(request.getOrderId(), OrderEntity.StatusEnum.DA_GIAO);
        if (ObjectUtils.isEmpty(order)) {
            throw new NotFoundException(HttpStatus.NOT_FOUND.value(), "Order không tồn tại");
        }
        Instant start = order.getCreateAt();
        Calendar calendar = Calendar.getInstance();
        Calendar calendar1 = Calendar.getInstance();
        calendar.setTime(Date.from(start));
        calendar1.setTime(new Date());
        long day  = (calendar1.getTime().getTime() - calendar.getTime().getTime()) / (24 * 3600 * 1000);
        System.out.println(day);
        if(day > 2){
            throw new NotFoundException(HttpStatus.NOT_FOUND.value(), "Order đã quá hạn");
        }
        List<UUID> productIds =  request.getProducts().stream().map(ExchangeRequest.ExchangeDetail::getProductId).collect(Collectors.toList());
        List<OrderDetailEntity> orderDetailEntityList = orderDetailRepository.
                findByOrderIdAndProductIdIn(request.getOrderId(),productIds
                       );
        if (request.getProducts().size() != orderDetailEntityList.size()) {
            throw new NotFoundException(HttpStatus.NOT_FOUND.value(), "Trong order không có sản phẩm này");
        }
        request.getProducts().forEach(exchangeDetail -> {
          Optional<ExchangeDetailEntity> exchangeDetailEntity = this.exchangeDetailRepository.findByOrderAndProduct(exchangeDetail.getProductId(),exchangeDetail.getSizeId(),request.getOrderId());
          if(exchangeDetailEntity.isPresent()){
              Optional<ProductEntity> productEntity =  this.productRepository.findID(exchangeDetailEntity.get().getProductId());
              Optional<SizeEntity> sizeEntity = this.sizeRepository.findById(exchangeDetailEntity.get().getSizeId());
              throw new BadRequestException("Sản phẩm " + productEntity.get().getNameProduct() + "Kích thước "+sizeEntity.get().getSize() + " đã được đổi một lần trong hóa đơn này");
          }
        });
        ExchangeEntity exchangeEntity = this.modelMapper.map(request,ExchangeEntity.class);
        exchangeEntity.setId(UUID.randomUUID());
        this.exchangeRepository.save(exchangeEntity);
        List<ExchangeDetailEntity> exchangeDetailEntities = new ArrayList<>();
        List<WaitingProductEntity> waitingProductEntities = new ArrayList<>();
        request.getProducts().forEach(product -> {
            ExchangeDetailEntity exchangeDetailEntity = new ExchangeDetailEntity();
            WaitingProductEntity waitingProductEntity = new WaitingProductEntity();
            exchangeDetailEntity.setExchangeId(exchangeEntity.getId());
            exchangeDetailEntity.setId(UUID.randomUUID());
            exchangeDetailEntity.setProductId(product.getProductId());
            exchangeDetailEntity.setSizeId(product.getSizeId());
            exchangeDetailEntity.setQuantity(product.getQuantity());
            exchangeDetailEntities.add(exchangeDetailEntity);

            waitingProductEntity.setId(UUID.randomUUID());
            waitingProductEntity.setProductId(product.getProductId());
            waitingProductEntity.setNote(exchangeEntity.getNote());
            waitingProductEntity.setQuantity(product.getQuantity());
            waitingProductEntity.setSizeId(product.getSizeId());
            waitingProductEntity.setCreateBy(exchangeEntity.getCreateBy());
            waitingProductEntities.add(waitingProductEntity);
        });
        this.exchangeDetailRepository.saveAll(exchangeDetailEntities);
        this.waitingProductRepository.saveAll(waitingProductEntities);
        return this.modelMapper.map(exchangeEntity,ExchangeDTO.class);
    }

    @Override
    public ExchangeDTO updateExchange(UUID id,ExchangeRequest request) {
        ExchangeEntity exchangeEntity = this.exchangeRepository.findById(id).orElseThrow(() -> new NotFoundException(HttpStatus.NO_CONTENT.value(), "Hóa đơn đổi trả này không tồn tại"));
        exchangeEntity.setStatus(request.getStatus());
        return this.modelMapper.map(this.exchangeRepository.save(exchangeEntity),ExchangeDTO.class);
    }

    @Override
    public ExchangeDTO findById(UUID id) {
        ExchangeEntity exchangeEntity = this.exchangeRepository.findById(id).orElseThrow(() -> new NotFoundException(HttpStatus.NO_CONTENT.value(), "Hóa đơn đổi trả này không tồn tại"));
        ExchangeDTO exchangeDTO = this.modelMapper.map(exchangeEntity,ExchangeDTO.class);
        OrderEntity order = this.orderRepository.findById(exchangeDTO.getOrderId()).get();
        OrderDTO orderDTO = this.modelMapper.map(order,OrderDTO.class);
        orderDTO.setUser(this.modelMapper.map(this.userRepository.findById(orderDTO.getUserId()).get(), UserDTO.class));
        exchangeDTO.setOrderDTO(orderDTO);
        List<ProductOrderDto> productOrderDtos = new ArrayList<>();
        List<ExchangeDetailEntity> exchangeDetailEntities = this.exchangeDetailRepository.findByExchangeId(exchangeDTO.getId());
        exchangeDetailEntities.forEach(exchangeDetailEntity -> {
            ProductEntity productEntity = this.productRepository.findID(exchangeDetailEntity.getProductId()).get();
            SizeEntity entity = this.sizeRepository.getSizeEntitiesBy(exchangeDetailEntity.getSizeId()).get();
            ProductOrderDto productOrderDto = new ProductOrderDto();
            productOrderDto.setProductId(exchangeDetailEntity.getProductId());
            productOrderDto.setQuantity(exchangeDetailEntity.getQuantity());
            productOrderDto.setNameProduct(productEntity.getNameProduct());
            productOrderDto.setSize(entity.getSize());
            productOrderDto.setSizeId(entity.getSizeId());
            List<ProductImageEntity> productImageEntity = this.productImageRepository.findByProductId(productEntity.getProductId());
            productOrderDto.setImageUrl(productImageEntity.stream().map(ProductImageEntity::getImageUrl).collect(Collectors.toList()));
            Optional<OrderDetailEntity> orderDetailEntity = this.orderDetailRepository.findByOrderIdAndProductIdAndSizeId(exchangeEntity.getOrderId(), productOrderDto.getProductId(),productOrderDto.getSizeId());
            productOrderDto.setPrice(orderDetailEntity.get().getPrice());
            productOrderDtos.add(productOrderDto);
        });
        exchangeDTO.setProductOrderDtoList(productOrderDtos);
        return exchangeDTO;
    }

    @Override
    public List<ExchangeEntity> getAllExchangeByTime(UUID orderId){
//        ExchangeEntity exchangeEntity = exchangeRepository.findByOrder(orderId).get(0);
//        List<ExchangeEntity> exchangeEntities =exchangeRepository.findByOrderIdAndTime(orderId,exchangeEntity.getTime());
//        return exchangeEntities;
        return null;
    }
    @Override
    public List<ExchangeEntity> getAllExchangeByOrder(UUID orderId){
        List<ExchangeEntity> exchangeEntityList = exchangeRepository.findByOrder(orderId);
        return exchangeEntityList;
    }
}
