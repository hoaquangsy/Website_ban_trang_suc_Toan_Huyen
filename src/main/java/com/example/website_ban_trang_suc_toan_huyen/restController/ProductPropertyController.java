package com.example.website_ban_trang_suc_toan_huyen.restController;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductPropertyController {

    @GetMapping
    public ResponseEntity<?> getAllProductProperty() {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductPropertyById(@PathVariable("id") Integer id) {
        return null;
    }

    @PostMapping
    public ResponseEntity<?> addProductProperty() {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProductProperty(@PathVariable("id") Integer id) {
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProductProperty(@PathVariable("id") Integer id ) {
        return null;
    }
}
