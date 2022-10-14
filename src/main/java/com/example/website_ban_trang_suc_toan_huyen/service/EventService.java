package com.example.website_ban_trang_suc_toan_huyen.service;

import com.example.website_ban_trang_suc_toan_huyen.entity.dto.CategoryDto;
import com.example.website_ban_trang_suc_toan_huyen.entity.dto.EventDto;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.CategoryRequest;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.EventRequest;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface EventService {
    EventDto createEvent(EventRequest newEvent);

    EventDto updateEvent(UUID id, EventRequest dto);

    EventDto delete(UUID id);

    EventDto getEventById(UUID id);

    List<EventDto> getAllEvent();
}
