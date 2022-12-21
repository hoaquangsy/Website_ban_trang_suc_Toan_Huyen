package com.example.website_ban_trang_suc_toan_huyen.service;

import com.example.website_ban_trang_suc_toan_huyen.entity.dto.EventDto;
import com.example.website_ban_trang_suc_toan_huyen.entity.dto.response.PageDTO;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.EventRequest;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.EventSearchRequest;

import java.text.ParseException;
import java.util.List;
import java.util.UUID;

public interface EventService {
    EventDto createEvent(EventRequest newEvent);

    EventDto updateEvent(UUID id, EventRequest dto);

    EventDto delete(UUID id);

    EventDto getEventById(UUID id);

    List<EventDto> getAllEvent();

    PageDTO search(EventSearchRequest eventSearchRequest) throws ParseException;
}
