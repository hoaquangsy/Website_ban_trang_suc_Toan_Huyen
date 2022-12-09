package com.example.website_ban_trang_suc_toan_huyen.dao;

import com.example.website_ban_trang_suc_toan_huyen.entity.entity.AccessoryEntity;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.CalendarEntity;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.CalendarSearchRequest;
import com.example.website_ban_trang_suc_toan_huyen.support.enums.AccessoryStatus;

import java.text.ParseException;
import java.util.List;

public interface CalendarDAO {
    List<CalendarEntity> search(CalendarSearchRequest calendarSearchRequest) throws ParseException;

    Long count(CalendarSearchRequest calendarSearchRequest) throws ParseException;
}
