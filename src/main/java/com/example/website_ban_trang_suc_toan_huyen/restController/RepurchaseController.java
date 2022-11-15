package com.example.website_ban_trang_suc_toan_huyen.restController;

import com.example.website_ban_trang_suc_toan_huyen.entity.dto.response.PageDTO;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.OrderEntity;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.OrderRequest;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.OrderUpdate;
import com.example.website_ban_trang_suc_toan_huyen.payload.response.SampleResponse;
import com.example.website_ban_trang_suc_toan_huyen.service.RepurchaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.UUID;

@Tag(
        description = "Repurchase controller",
        name = "Các api về hóa đơn mua lại "
)
@RestController
@Slf4j
@RequestMapping(value = "/api/v1/repurchase")
@CrossOrigin("*")
public class RepurchaseController {

    @Autowired
    private RepurchaseService repurchaseService;
    @Operation(summary = "Lưu Repurchase", description = "Lưu order")
    @PostMapping
    public ResponseEntity<?> saveRepurchase(@RequestBody @Valid OrderRequest orderRequest) {
        return ResponseEntity.ok(SampleResponse.success(this.repurchaseService.saveRepurchase(orderRequest)));
    }

    @Operation(summary = "update Repurchase", description = "update Repurchase")
    @PostMapping("/{id}")
    public ResponseEntity<?> updateRepurchase(@PathVariable("id") UUID id,
                                         @RequestBody OrderUpdate update) {
        return ResponseEntity.ok(SampleResponse.success(repurchaseService.updateRepurchase(id,update.getStatus())));
    }

    @Operation(summary = "Search hóa đơn mua lại")
    @GetMapping
    public PageDTO search(@RequestParam(value = "pageIndex",defaultValue = "1",required = false) Integer pageIndex,
                          @RequestParam(value = "pageSize",defaultValue = "10",required = false) Integer pageSize,
                          @RequestParam(value = "keyword",defaultValue = "",required = false) String keyword,
                          @RequestParam(value = "status",required = false) OrderEntity.StatusEnum status,
                          @RequestParam(value = "payMethod",required = false) OrderEntity.PaymentMethod payMethod,
                          @RequestParam(value = "purchaseType",required = false) OrderEntity.OrderType orderType,
                          @RequestParam(value = "startDate",required = false) String startDate,
                          @RequestParam(value = "endDate",required = false) String endDate,
                          @RequestParam(value = "startPrice",required = false) BigDecimal startPrice,
                          @RequestParam(value = "endPrice",required = false) BigDecimal endPrice,
                          @RequestParam(value = "userId",required = false) UUID userId,
                          @RequestParam(value = "sortBy",required = false) String sortBy) throws ParseException {
        return this.repurchaseService.search(pageIndex,pageSize,keyword,status,payMethod,orderType,startDate,endDate,startPrice,endPrice,userId,sortBy);
    }
}
