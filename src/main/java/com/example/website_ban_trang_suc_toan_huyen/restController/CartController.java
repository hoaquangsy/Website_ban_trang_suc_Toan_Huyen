package com.example.website_ban_trang_suc_toan_huyen.restController;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequestMapping(value = "api/v1/cart")
@RestController
public class CartController {

    @GetMapping
    public ResponseEntity<?> getAllCart() {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCartById(@PathVariable("id") Integer id) {
        return null;
    }

    @PostMapping
    public ResponseEntity<?> addCart() {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCart(@PathVariable("id") Integer id) {
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCart(@PathVariable("id") Integer id ) {
        return null;
    }
}
