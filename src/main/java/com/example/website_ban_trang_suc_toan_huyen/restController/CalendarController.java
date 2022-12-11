package com.example.website_ban_trang_suc_toan_huyen.restController;

import com.example.website_ban_trang_suc_toan_huyen.entity.dto.CalendarDTO;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.CalendarEntity;
import com.example.website_ban_trang_suc_toan_huyen.payload.response.SampleResponse;
import com.example.website_ban_trang_suc_toan_huyen.service.CalendarService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@Tag(
        description = "calendar controller",
        name = "Các api về đặt cọc, đặt lịch"
)
@CrossOrigin("*")
@RequestMapping(value = "api/v1/calendar")
@RestController
public class CalendarController {
    @Autowired
    private CalendarService calendarService;


    @PostMapping("")
    public ResponseEntity<?> createCalendar(@RequestBody @Valid CalendarDTO calendarDTO) {
        return ResponseEntity.ok(SampleResponse.success(calendarService.createCalendar(calendarDTO)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCalendarByUser(@PathVariable(name = "id") UUID id, @RequestParam(name = "status")CalendarEntity.Status status) {
        return ResponseEntity.ok(SampleResponse.success(calendarService.updateCalendarByUser(id, status.getCode())));
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getCalendar(@PathVariable(name = "id") UUID id) {
        return ResponseEntity.ok(SampleResponse.success(calendarService.getCalendar(id)));
    }
    @PutMapping("admin/{id}")
    public ResponseEntity<?> updateCalendarByAdmin(@PathVariable(name = "id") UUID id, @RequestParam(name = "status")CalendarEntity.Status status) {
        return ResponseEntity.ok(SampleResponse.success(calendarService.updateCalendarByUser(id, status.getCode())));
    }

}
