package com.example.website_ban_trang_suc_toan_huyen.restController;

import com.example.website_ban_trang_suc_toan_huyen.entity.dto.response.PageDTO;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.WaitingProductRequest;
import com.example.website_ban_trang_suc_toan_huyen.payload.response.SampleResponse;
import com.example.website_ban_trang_suc_toan_huyen.service.WaitingProductService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/waiting-product")
public class WaitingProductController {
    @Autowired
    private WaitingProductService waitingProductService;

    @Operation(summary = "Lấy tất cả waiting product")
    @GetMapping()
    public ResponseEntity<?> getAllWaitingProduct() {
        return ResponseEntity.ok(SampleResponse.success(waitingProductService.findAll()));
    }
    @Operation(summary = "Tạo  waiting product")
    @PostMapping()
    public ResponseEntity<?> CreateWaitingProduct(
            @RequestBody WaitingProductRequest waitingProductRequest
            ) {
        return ResponseEntity.ok(SampleResponse.success(waitingProductService.createWaitingProduct(waitingProductRequest)));
    }
    @Operation(summary = "Lấy  waiting product của order")
    @PostMapping("/order/{id}")
    public ResponseEntity<?> findAllByOrderId(
            @PathVariable(name = "id") UUID orderId
    ) {
        return ResponseEntity.ok(SampleResponse.success(waitingProductService.findAllByOrderId(orderId)));
    }
    @Operation(summary = "Thêm  waiting product vào product")
    @PostMapping("/order/{id}")
    public ResponseEntity<?> sendProduct(
            @RequestBody WaitingProductRequest waitingProductRequest
    ) {
        return ResponseEntity.ok(SampleResponse.success(waitingProductService.sendProduct(waitingProductRequest)));
    }

}
