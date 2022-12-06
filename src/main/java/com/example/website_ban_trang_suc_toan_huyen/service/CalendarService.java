package com.example.website_ban_trang_suc_toan_huyen.service;

import com.example.website_ban_trang_suc_toan_huyen.entity.dto.CalendarDTO;

import java.util.UUID;

public interface CalendarService {
    CalendarDTO createCalendar(CalendarDTO calendarDTO);

    CalendarDTO updateCalendarByUser(UUID calendarId, Integer status);

    CalendarDTO getCalendar(UUID calendarId);

    CalendarDTO updateCalendarByAdmin(UUID calendarId, Integer status);
}
