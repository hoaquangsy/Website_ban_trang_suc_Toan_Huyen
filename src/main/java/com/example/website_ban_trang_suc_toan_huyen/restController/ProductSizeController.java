package com.example.website_ban_trang_suc_toan_huyen.restController;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductSizeController {

    @GetMapping
    public ResponseEntity<?> getAllProductSize() {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductSizeById(@PathVariable("id") Integer id) {
        return null;
    }

    @PostMapping
    public ResponseEntity<?> addProductSize() {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProductSize(@PathVariable("id") Integer id) {
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProductSize(@PathVariable("id") Integer id ) {
        return null;
    }
}
