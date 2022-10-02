package com.example.website_ban_trang_suc_toan_huyen.restController;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cartdetail")

public class CartDetailController {

    @GetMapping
    public ResponseEntity<?> getCartDetailAll() {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCartDetailById(@PathVariable("id") Integer id) {
        return null;
    }

    @PostMapping
    public ResponseEntity<?> addCartDetail() {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCartDetail(@PathVariable("id") Integer id) {
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCartDetail(@PathVariable("id") Integer id ) {
        return null;
    }
}
