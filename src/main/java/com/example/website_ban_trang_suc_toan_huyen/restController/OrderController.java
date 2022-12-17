package com.example.website_ban_trang_suc_toan_huyen.restController;


import com.example.website_ban_trang_suc_toan_huyen.entity.dto.OrderDTO;
import com.example.website_ban_trang_suc_toan_huyen.entity.dto.response.PageDTO;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.MaterialEntity;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.OrderEntity;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.OrderRequest;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.OrderUpdate;
import com.example.website_ban_trang_suc_toan_huyen.payload.response.SampleResponse;
import com.example.website_ban_trang_suc_toan_huyen.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Tag(
        description = "order controller",
        name = "Các api về order "
)
@RestController
@RequestMapping("/api/v1/order")
@CrossOrigin("*")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Operation(summary = "Lấy all order", description = "Lấy all order")
    @GetMapping("/all")
    public ResponseEntity<?> getAllOrder(@RequestParam(value = "page") int page,
                                         @RequestParam(value = "page_size") int pageSize) {
        Page<OrderDTO> productDtoPage = orderService.getAllOrder(page, pageSize);
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
        return ResponseEntity.ok(SampleResponse.success(orderService.findByUser(page, pageSize, userId)));
    }

    @Operation(summary = "Lưu order", description = "Lưu order")
    @PostMapping
    public ResponseEntity<?> saveOrder(@RequestBody @Valid OrderRequest orderRequest) {
        return ResponseEntity.ok(SampleResponse.success(orderService.saveOrder(orderRequest)));
    }

    @Operation(summary = "update order", description = "update order")
    @PostMapping("/{id}")
    public ResponseEntity<?> updateOrder(@PathVariable("id") UUID id,
                                         @RequestBody OrderUpdate update) {
        return ResponseEntity.ok(SampleResponse.success(orderService.update(id, update.getStatus())));
    }
    @Operation(summary = "update wait order", description = "update wait order")
    @PostMapping("/{id}/orderwait")
    public ResponseEntity<?> updateOrderWait(@PathVariable("id") UUID id,
                                         @RequestBody OrderRequest  update) {
        return ResponseEntity.ok(SampleResponse.success(orderService.updateWaitOrder(id,update)));
    }
    @Operation(summary = "export order", description = "export order")
    @GetMapping("export/{id}")
    public ResponseEntity<?> exportOrder(@PathVariable("id") UUID id
    ) {
        ByteArrayInputStream byteArrayInputStream = orderService.exportPdf(id);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Despoisition","inline; filename:order.pdf");
        return ResponseEntity.ok().headers(httpHeaders).contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(byteArrayInputStream));
    }
    @Operation(summary = "Search Chất liệu")
    @GetMapping
    public PageDTO search(@RequestParam(value = "pageIndex", defaultValue = "1", required = false) Integer pageIndex,
                          @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
                          @RequestParam(value = "keyword", defaultValue = "", required = false) String keyword,
                          @RequestParam(value = "status", required = false) OrderEntity.StatusEnum status,
                          @RequestParam(value = "payMethod", required = false) OrderEntity.PaymentMethod payMethod,
                          @RequestParam(value = "purchaseType", required = false) OrderEntity.OrderType orderType,
                          @RequestParam(value = "startDate", required = false) String startDate,
                          @RequestParam(value = "endDate", required = false) String endDate,
                          @RequestParam(value = "startPrice", required = false) BigDecimal startPrice,
                          @RequestParam(value = "endPrice", required = false) BigDecimal endPrice,
                          @RequestParam(value = "userId", required = false) UUID userId,
                          @RequestParam(value = "sortBy", required = false) String sortBy) throws ParseException {
        return this.orderService.search(pageIndex, pageSize, keyword, status, payMethod, orderType, startDate, endDate, startPrice, endPrice, userId, sortBy);
    }
    @GetMapping("/list")
    public ResponseEntity<?> getOrderByListId(@RequestParam(value = "status", required = false) OrderEntity.StatusEnum status,
                                              @RequestParam(value = "userId", required = false) UUID userId
    ) {
        return ResponseEntity.ok(SampleResponse.success(orderService.findByStatusAndUserId(status, userId)));
    }
}
