package com.example.website_ban_trang_suc_toan_huyen.service.impl;

import com.example.website_ban_trang_suc_toan_huyen.entity.dto.EventDto;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.EventEntity;
import com.example.website_ban_trang_suc_toan_huyen.exception.NotFoundException;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.EventRequest;
import com.example.website_ban_trang_suc_toan_huyen.repository.EventRepository;
import com.example.website_ban_trang_suc_toan_huyen.service.EventService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public EventDto createEvent(EventRequest newEvent) {
        EventEntity eventEntity = this.modelMapper.map(newEvent,EventEntity.class);
        eventEntity.setEventId(UUID.randomUUID());
        eventEntity.setDeleted(Boolean.FALSE);
        String code = LocalDate.now().toString().replaceAll("-","");
        eventEntity.setCode("EVENT_"+code);
        return this.modelMapper.map(this.eventRepository.save(eventEntity), EventDto.class);
    }

    @Override
    public EventDto updateEvent(UUID id, EventRequest dto) {
        EventEntity eventEntity = this.eventRepository.findId(id).orElseThrow(() -> new NotFoundException(HttpStatus.NOT_FOUND.value(),"Event Not Found"));
        eventEntity.setDiscount(dto.getDiscount());
        eventEntity.setStartDate(dto.getStartDate());
        eventEntity.setEndDate(dto.getEndDate());
        eventEntity.setDescription(dto.getDescription());
        return this.modelMapper.map(this.eventRepository.save(eventEntity), EventDto.class);
    }

    @Override
    public EventDto delete(UUID id) {
        EventEntity eventEntity = this.eventRepository.findId(id).orElseThrow(() -> new NotFoundException(HttpStatus.NOT_FOUND.value(),"Event Not Found"));
        eventEntity.setDeleted(Boolean.TRUE);
        return this.modelMapper.map(this.eventRepository.save(eventEntity), EventDto.class);
    }

    @Override
    public EventDto getEventById(UUID id) {
        EventEntity eventEntity = this.eventRepository.findId(id).orElseThrow(() -> new NotFoundException(HttpStatus.NOT_FOUND.value(),"Event Not Found"));
        return this.modelMapper.map(eventEntity,EventDto.class);
    }

    @Override
    public List<EventDto> getAllEvent() {
        List<EventEntity> eventEntities = this.eventRepository.getAll();

        return eventEntities.stream().map(eventEntity -> this.modelMapper.map(eventEntity, EventDto.class)).collect(Collectors.toList());
    }
}
