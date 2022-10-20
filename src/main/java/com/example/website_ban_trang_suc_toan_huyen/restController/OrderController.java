package com.example.website_ban_trang_suc_toan_huyen.restController;


import com.example.website_ban_trang_suc_toan_huyen.entity.dto.OrderDTO;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.OrderRequest;
import com.example.website_ban_trang_suc_toan_huyen.payload.response.SampleResponse;
import com.example.website_ban_trang_suc_toan_huyen.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@Tag(
        description = "order controller",
        name = "Các api về order "
)
@RestController
@RequestMapping("/api/v1/order")


public class OrderController {
    @Autowired
    private OrderService orderService;
    @Operation(summary = "Lấy all order", description = "Lấy all order")
    @GetMapping("/all")
    public ResponseEntity<?> getAllOrder(@RequestParam(value = "page") int page,
                                         @RequestParam(value = "page_size") int pageSize) {
        Page<OrderDTO> productDtoPage = orderService.getAllOrder(page,pageSize);
        return ResponseEntity.ok(productDtoPage);
    }

    @Operation(summary = "Lấy order theo id", description = "Lấy order theo id")
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(SampleResponse.success(orderService.findOrder(id)));
    }
    @Operation(summary = "Lấy order theo  useId", description = "Lấy order theo  useId")
    @GetMapping("/user")
    public ResponseEntity<?> getOrderByUserId(@RequestParam(value = "page") int page,
                                              @RequestParam(value = "page_size") int pageSize,
                                              @RequestParam("user_id") UUID userId) {
        return ResponseEntity.ok(SampleResponse.success(orderService.findByUser(page,pageSize,userId)));
    }

    @Operation(summary = "Lưu order", description = "Lưu order")
    @PostMapping
    public ResponseEntity<?> saveOrder(@RequestBody @Valid OrderRequest orderRequest) {
        return ResponseEntity.ok(SampleResponse.success(orderService.saveOrder(orderRequest)));
    }

    @Operation(summary = "update order", description = "update order")
    @PutMapping("/update")
    public ResponseEntity<?> updateOrder(@RequestParam("id") UUID id,
                                         @RequestParam("status") Integer status) {
        return ResponseEntity.ok(SampleResponse.success(orderService.update(id,status)));
    }
}
