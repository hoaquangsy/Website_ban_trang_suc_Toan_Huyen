package com.example.website_ban_trang_suc_toan_huyen.service.impl;

import com.example.website_ban_trang_suc_toan_huyen.dao.CalendarDAO;
import com.example.website_ban_trang_suc_toan_huyen.entity.dto.CalendarDTO;
import com.example.website_ban_trang_suc_toan_huyen.entity.dto.ProductDto;
import com.example.website_ban_trang_suc_toan_huyen.entity.dto.SizeDto;
import com.example.website_ban_trang_suc_toan_huyen.entity.dto.response.PageDTO;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.CalendarEntity;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.OrderEntity;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.ProductEntity;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.SizeEntity;
import com.example.website_ban_trang_suc_toan_huyen.exception.NotFoundException;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.CalendarRequest;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.CalendarSearchRequest;
import com.example.website_ban_trang_suc_toan_huyen.repository.CalendarRepository;
import com.example.website_ban_trang_suc_toan_huyen.repository.OrderRepository;
import com.example.website_ban_trang_suc_toan_huyen.repository.ProductRepository;
import com.example.website_ban_trang_suc_toan_huyen.repository.SizeRepository;
import com.example.website_ban_trang_suc_toan_huyen.service.CalendarService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
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

    @Autowired
    private CalendarDAO calendarDAO;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SizeRepository sizeRepository;

    @Override
    public CalendarDTO createCalendar(CalendarRequest calendarRequest) {
        CalendarEntity calendar = modelMapper.map(calendarRequest,CalendarEntity.class);
        calendar.setTime(calendar.getTime().plusHours(7));
        calendar.setId(UUID.randomUUID());
        return modelMapper.map(calendarRepository.save(calendar), CalendarDTO.class);
    }

    @Override
    public CalendarDTO updateCalendarByUser(UUID calendarId, CalendarRequest calendarRequest) {
        CalendarEntity calendar = calendarRepository.findById(calendarId).orElseThrow(() -> new NotFoundException(HttpStatus.NOT_FOUND.value(), "Sản phẩm không tồn tại"));
        calendar.setTime(calendar.getTime().plusHours(7));
        calendar.setUserName(calendarRequest.getUserName());
        calendar.setPhoneNumber(calendarRequest.getPhoneNumber());
        calendar.setEmail(calendarRequest.getEmail());
        calendar.setNote(calendar.getNote());
        calendar.setProductId(calendar.getProductId());
        calendar.setSizeId(calendar.getSizeId());
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
    public CalendarDTO changeStatus(UUID calendarId, CalendarRequest calendarRequest) {
        CalendarEntity calendar = calendarRepository.findById(calendarId).orElseThrow(() -> new NotFoundException(HttpStatus.NOT_FOUND.value(), "Sản phẩm không tồn tại"));
        //  calendar.setTime(calendarRequest.getTime());
        calendar.setStatus(calendarRequest.getStatus());
        return modelMapper.map(calendarRepository.save(calendar), CalendarDTO.class);
    }

    @Override
    public PageDTO search(CalendarSearchRequest calendarSearchRequest) throws ParseException {
        Long count = this.calendarDAO.count(calendarSearchRequest);
        if(count == 0L){
            return PageDTO.empty();
        }
        List<CalendarEntity> calendarEntities = this.calendarDAO.search(calendarSearchRequest);
        List<CalendarDTO> calendarDTOS  = new ArrayList<>();
        calendarEntities.forEach(exchangeEntity -> {
            CalendarDTO calendarDTO = this.modelMapper.map(exchangeEntity,CalendarDTO.class);
            calendarDTO.setSize(this.modelMapper.map(this.sizeRepository.findById(calendarDTO.getSizeId()).orElse(new SizeEntity()), SizeDto.class));
            calendarDTO.setProduct(this.modelMapper.map(this.productRepository.findID(calendarDTO.getProductId()).orElse(new ProductEntity()), ProductDto.class));
            calendarDTOS.add(calendarDTO);
        });
        return new PageDTO(calendarDTOS,calendarSearchRequest.getPageIndex(),calendarSearchRequest.getPageSize(),count);

    }
}