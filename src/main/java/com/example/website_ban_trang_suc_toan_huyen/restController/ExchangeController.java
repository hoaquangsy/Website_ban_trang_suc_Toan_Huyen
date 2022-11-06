package com.example.website_ban_trang_suc_toan_huyen.restController;


import com.example.website_ban_trang_suc_toan_huyen.payload.request.ExchangeRequest;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.ExchangeSearchRequest;
import com.example.website_ban_trang_suc_toan_huyen.payload.response.SampleResponse;
import com.example.website_ban_trang_suc_toan_huyen.service.ExchangeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.UUID;

@Tag(
        description = "exchange controller",
        name = "Các api về exchange "
)
@RestController
@RequestMapping("/api/v1/exchange")
@CrossOrigin("*")
public class ExchangeController {
    @Autowired
    private ExchangeService exchangeService;

    @GetMapping()
    public ResponseEntity<?> search(ExchangeSearchRequest exchangeSearchRequest) throws ParseException {
        return ResponseEntity.ok(SampleResponse.success(this.exchangeService.search(exchangeSearchRequest)));
    }
    @GetMapping("/order/time/{id}")
    public ResponseEntity<?> getAllExchangeByTime(
            @PathVariable(name = "id") UUID orderId) {
        return ResponseEntity.ok(exchangeService.getAllExchangeByOrder(orderId));
    }
    @GetMapping("/order/{id}")
    public ResponseEntity<?> getAllExchangeByOrder(
            @PathVariable(name = "id") UUID orderId) {
        return ResponseEntity.ok(SampleResponse.success(exchangeService.findById(orderId)));
    }

    @PostMapping
    public ResponseEntity<?> addExchange(@RequestBody ExchangeRequest request) {
        return ResponseEntity.ok(exchangeService.createExchange(request));
    }

    @PostMapping("{id}")
    public ResponseEntity<?> updateExchange(
            @PathVariable(name = "id") UUID exchangeId,
            @RequestBody ExchangeRequest request) {
        return ResponseEntity.ok(exchangeService.updateExchange(exchangeId,request));
    }



}
