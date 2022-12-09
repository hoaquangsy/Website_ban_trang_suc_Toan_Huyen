package com.example.website_ban_trang_suc_toan_huyen.service;

import com.example.website_ban_trang_suc_toan_huyen.entity.dto.CalendarDTO;
import com.example.website_ban_trang_suc_toan_huyen.entity.dto.response.PageDTO;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.CalendarEntity;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.CalendarRequest;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.CalendarSearchRequest;

import java.text.ParseException;
import java.util.UUID;

public interface CalendarService {
    CalendarDTO createCalendar(CalendarRequest calendarRequest);

    CalendarDTO updateCalendarByUser(UUID calendarId,CalendarRequest calendarRequest);

    CalendarDTO getCalendar(UUID calendarId);

    CalendarDTO changeStatus(UUID calendarId,CalendarRequest calendarRequest);

    PageDTO search(CalendarSearchRequest calendarSearchRequest) throws ParseException;
}