package com.example.website_ban_trang_suc_toan_huyen.service.impl;

import com.example.website_ban_trang_suc_toan_huyen.entity.dto.CalendarDTO;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.CalendarEntity;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.OrderEntity;
import com.example.website_ban_trang_suc_toan_huyen.exception.NotFoundException;
import com.example.website_ban_trang_suc_toan_huyen.repository.CalendarRepository;
import com.example.website_ban_trang_suc_toan_huyen.repository.OrderRepository;
import com.example.website_ban_trang_suc_toan_huyen.service.CalendarService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Optional;
import java.util.UUID;

@Service
public class CalendarServiceImpl implements CalendarService {
    @Autowired
    private CalendarRepository calendarRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CalendarDTO createCalendar(CalendarDTO calendarDTO) {
        Optional<OrderEntity> orderEntity = orderRepository.findById(calendarDTO.getOrderId());
        if (orderEntity.isEmpty()) {
            throw new NotFoundException(HttpStatus.NOT_FOUND.value(), "Order không tồn tại");
        }
        if (!orderEntity.get().getPurchaseType().equals(OrderEntity.OrderType.DEPOSIT)) {
            throw new NotFoundException(HttpStatus.NOT_FOUND.value(), "Order phải order đặt cọc");
        }
        CalendarEntity calendar = new CalendarEntity();
        calendar.setTime(calendarDTO.getTime());
        calendar.setArrears(calendarDTO.getArrears());
        calendar.setCustomerMoney(orderEntity.get().getCustomerMoney());
        calendar.setUserName(calendarDTO.getUserName());
        calendar.setPhoneNumber(calendarDTO.getPhoneNumber());
        calendar.setOrderId(orderEntity.get().getId());
        calendar.setId(UUID.randomUUID());
        calendar.setStatus(CalendarEntity.Status.WAIT_CONFIRM.getCode());
        return modelMapper.map(calendarRepository.save(calendar), CalendarDTO.class);
    }

    @Override
    public CalendarDTO updateCalendarByUser(UUID calendarId, Integer status) {
        CalendarEntity calendar = calendarRepository.findById(calendarId).get();
        if (ObjectUtils.isEmpty(calendar)) {
            throw new NotFoundException(HttpStatus.NOT_FOUND.value(), "Calendar không tồn tại");
        }
        OrderEntity orderEntity = orderRepository.findById(calendar.getOrderId()).get();
        if (calendar.getStatus().equals(CalendarEntity.Status.ACTIVE.getCode()) && status.equals(CalendarEntity.Status.CLOSE.getCode())){
            throw new NotFoundException(HttpStatus.BAD_REQUEST.value(), "Không thể hủy calendar đã xác nhận");
        }
        calendar.setStatus(status);
        if (status.equals(CalendarEntity.Status.CLOSE.getCode())) {
            orderEntity.setStatus(OrderEntity.StatusEnum.HUY);
        }
        orderRepository.save(orderEntity);
        return modelMapper.map(calendarRepository.save(calendar), CalendarDTO.class);
    }
    @Override
    public CalendarDTO getCalendar(UUID calendarId) {
        CalendarEntity calendar = calendarRepository.findById(calendarId).get();
        if (ObjectUtils.isEmpty(calendar)) {
            throw new NotFoundException(HttpStatus.NOT_FOUND.value(), "Calendar không tồn tại");
        }
        return  modelMapper.map(calendar,CalendarDTO.class);
    }

    @Override
    public CalendarDTO updateCalendarByAdmin(UUID calendarId, Integer status) {
        CalendarEntity calendar = calendarRepository.findById(calendarId).get();
        if (ObjectUtils.isEmpty(calendar)) {
            throw new NotFoundException(HttpStatus.NOT_FOUND.value(), "Calendar không tồn tại");
        }
        OrderEntity orderEntity = orderRepository.findById(calendar.getOrderId()).get();
        calendar.setStatus(status);
        if (status.equals(CalendarEntity.Status.DONE.getCode())) {
            orderEntity.setStatus(OrderEntity.StatusEnum.DA_GIAO);
        }
        if (status.equals(CalendarEntity.Status.CLOSE.getCode())) {
            orderEntity.setStatus(OrderEntity.StatusEnum.HUY);
        }
        orderRepository.save(orderEntity);
        return modelMapper.map(calendarRepository.save(calendar), CalendarDTO.class);
    }
}
