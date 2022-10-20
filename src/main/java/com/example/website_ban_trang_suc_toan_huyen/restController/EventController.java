package com.example.website_ban_trang_suc_toan_huyen.restController;

import com.example.website_ban_trang_suc_toan_huyen.entity.dto.AccessoryDTO;
import com.example.website_ban_trang_suc_toan_huyen.entity.dto.EventDto;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.AccessoryRequest;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.EventRequest;
import com.example.website_ban_trang_suc_toan_huyen.payload.response.SampleResponse;
import com.example.website_ban_trang_suc_toan_huyen.service.AccessoryService;
import com.example.website_ban_trang_suc_toan_huyen.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
@CrossOrigin("*")
@RestController()
@RequestMapping(value = "api/v1/event")
public class EventController {
    @Autowired
    private EventService eventService;

    @Operation(summary = "Get event by id")
    @GetMapping("/{id}")
    public ResponseEntity<?> getAccessoryById(@PathVariable("id") UUID id) {
      EventDto eventDTO = eventService.getEventById(id);
        return ResponseEntity.ok(SampleResponse.success(eventDTO));
    }

    @PostMapping
    public ResponseEntity<?> createEvent(@Validated @RequestBody EventRequest request) {
        EventDto eventDTO = eventService.createEvent(request);
        return ResponseEntity.ok(SampleResponse.success(eventDTO));
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") UUID id) {
        EventDto eventDTO = eventService.delete(id);
        return ResponseEntity.ok(SampleResponse.success(eventDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") UUID id ,
                                    @Validated @RequestBody EventRequest request) {
        return ResponseEntity.ok(SampleResponse.success(eventService.updateEvent(id,request)));

    }
    @GetMapping
    public ResponseEntity<?> getAllCategoryProduct() {
        return ResponseEntity.ok(SampleResponse.success(eventService.getAllEvent()));
    }
}
