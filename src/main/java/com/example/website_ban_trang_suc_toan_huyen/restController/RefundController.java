package com.example.website_ban_trang_suc_toan_huyen.restController;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/v1/refund")
public class RefundController {

    @GetMapping
    public ResponseEntity<?> getRefundAll() {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRefundById(@PathVariable("id") Integer id) {
        return null;
    }

    @PostMapping
    public ResponseEntity<?> addRefund() {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRefund(@PathVariable("id") Integer id) {
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRefund(@PathVariable("id") Integer id ) {
        return null;
    }
}
