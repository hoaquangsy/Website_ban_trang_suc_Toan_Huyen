package com.example.website_ban_trang_suc_toan_huyen.restController;


import com.example.website_ban_trang_suc_toan_huyen.entity.dto.ProductDto;
import com.example.website_ban_trang_suc_toan_huyen.entity.dto.RefundDto;
import com.example.website_ban_trang_suc_toan_huyen.exception.NotFoundException;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.OrderRequest;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.ProductRequest;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.RefundRequest;
import com.example.website_ban_trang_suc_toan_huyen.payload.response.DefaultPagingResponse;
import com.example.website_ban_trang_suc_toan_huyen.payload.response.RefundObject;
import com.example.website_ban_trang_suc_toan_huyen.payload.response.SampleResponse;
import com.example.website_ban_trang_suc_toan_huyen.service.OrderService;
import com.example.website_ban_trang_suc_toan_huyen.service.ProductService;
import com.example.website_ban_trang_suc_toan_huyen.service.RefundService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(
        description = "refund controller",
        name = "Các api về refund "
)
@RestController
@RequestMapping("/api/v1/refund")

public class RefundController {
    @Autowired
    private RefundService refundService;

    @Autowired
    private OrderService orderService;
    @Operation(summary = "Lấy tất cả refund", description =
            "page: trang hiện tại (bắt đầu từ 0), page_size: số record trong trang hiện tại ")
    @GetMapping
    public ResponseEntity<?> getAllRefund(@RequestParam(value = "page", defaultValue = "0",required = false) int page,
                                           @RequestParam(value = "page_size", defaultValue = "10",required = false) int pageSize) {
        try {
        Page<RefundDto> RefundDtoPage = refundService.findAllRefund(page,pageSize);
        return ResponseEntity.ok(RefundObject.success(RefundDtoPage));
        } catch (NotFoundException e){
            return ResponseEntity.ok(RefundObject.error(e));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRefundById(@PathVariable("id") String id) {
        try {
          return ResponseEntity.ok().body(RefundObject.success(refundService.getRefundById(id)));
        }catch (NotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(RefundObject.error(e));
        }
    }

    @PostMapping
    public ResponseEntity<?> addRefund(@RequestBody RefundRequest refundRequest) {
        try {
            RefundDto refundDto = refundService.createRefund(refundRequest);
            // sau khi tao phieu tra hang => chuyen trang thai hoa don thanh "Tra hang" VD :5
            orderService.update(refundDto.getOrderId(),5);
       return ResponseEntity.ok(RefundObject.success(refundDto));
        }catch (NotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(RefundObject.error(e));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRefund(@PathVariable("id") String id) {
        try {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(refundService.deleteRefund(id));
        }catch (NotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(RefundObject.error(e));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRefund(@PathVariable("id") String id
            ,@RequestBody RefundRequest refundRequest) {
        try {
            return ResponseEntity.accepted().body(refundService.updateRefund(id,refundRequest));
        }catch (NotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(RefundObject.error(e));
        }
    }
}
