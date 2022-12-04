package com.example.website_ban_trang_suc_toan_huyen.restController;

import com.example.website_ban_trang_suc_toan_huyen.entity.dto.response.PageDTO;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.WaitingProductRequest;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.WaitingProductSearchRequest;
import com.example.website_ban_trang_suc_toan_huyen.payload.response.SampleResponse;
import com.example.website_ban_trang_suc_toan_huyen.service.WaitingProductService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.UUID;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/waiting-product")
public class WaitingProductController {
    @Autowired
    private WaitingProductService waitingProductService;
    @Operation(summary = "Tạo  waiting product")
    @PostMapping()
    public ResponseEntity<?> CreateWaitingProduct(
            @RequestBody WaitingProductRequest waitingProductRequest
            ) {
        return ResponseEntity.ok(SampleResponse.success(waitingProductService.createWaitingProduct(waitingProductRequest)));
    }
    @Operation(summary = "Tạo  waiting product")
    @PostMapping("/{id}")
    public ResponseEntity<?> updateWaitingProduct(@PathVariable("id") UUID id,
            @RequestBody WaitingProductRequest waitingProductRequest
    ) {
        return ResponseEntity.ok(SampleResponse.success(waitingProductService.updateWaitingProduct(id,waitingProductRequest)));
    }
    @Operation(summary = "Lấy  waiting product của order")
    @PostMapping("/order/{id}")
    public ResponseEntity<?> findAllByOrderId(
            @PathVariable(name = "id") UUID orderId
    ) {
        return ResponseEntity.ok(SampleResponse.success(waitingProductService.findAllByOrderId(orderId)));
    }
    @Operation(summary = "Thêm  waiting product vào product")
    @PostMapping("/addProduct/{id}")
    public ResponseEntity<?> sendProduct(
            @PathVariable("id") UUID id
    ) {
        return ResponseEntity.ok(SampleResponse.success(waitingProductService.sendProduct(id)));
    }

    @Operation(summary = "Tìm kiếm sản phẩm cần gia công")
    @GetMapping("")
    public ResponseEntity<?> search(
           WaitingProductSearchRequest waitingProductRequest
    ) throws ParseException {
        return ResponseEntity.ok(SampleResponse.success(waitingProductService.search(waitingProductRequest)));
    }

}
