package com.example.website_ban_trang_suc_toan_huyen.restController;


import com.example.website_ban_trang_suc_toan_huyen.dao.StatisticalDAO;
import com.example.website_ban_trang_suc_toan_huyen.entity.dto.ProductStatistical;
import com.example.website_ban_trang_suc_toan_huyen.payload.response.SampleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/statistical")
@CrossOrigin("*")
public class StatisticalController {

    @Autowired
    private StatisticalDAO statisticalDAO;

    @GetMapping("revenue")
    public ResponseEntity<?> revenueTotal(@RequestParam("year") Integer year,@RequestParam(value = "isRepurchase",defaultValue = "false") Boolean isRepurchase) {
        return ResponseEntity.ok(SampleResponse.success(this.statisticalDAO.revenueTotal(year,isRepurchase)));
    }

    @GetMapping("category")
    public ResponseEntity<?> category() {
        return ResponseEntity.ok(SampleResponse.success(this.statisticalDAO.category()));
    }

    @GetMapping("material")
    public ResponseEntity<?> material() {
        return ResponseEntity.ok(SampleResponse.success(this.statisticalDAO.material()));
    }

    @GetMapping("productTop5")
    public ResponseEntity<?> top5_product(@RequestParam("year") Integer year) {
        List<ProductStatistical> productStatisticalList = this.statisticalDAO.productTop(year);
        Long count = this.statisticalDAO.countproductTop(year);
        productStatisticalList.forEach(productStatistical -> {
            productStatistical.setPersent(Double.parseDouble((productStatistical.getQuantity()+""))/count * 100);
        });
        return ResponseEntity.ok(SampleResponse.success(productStatisticalList));
    }

    @GetMapping("userTop5")
    public ResponseEntity<?> top5_user(@RequestParam("year") Integer year) {
        return ResponseEntity.ok(SampleResponse.success(this.statisticalDAO.userTop(year)));
    }
}
