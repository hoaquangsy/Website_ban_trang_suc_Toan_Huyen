package com.example.website_ban_trang_suc_toan_huyen.restController;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CategoryProductController {

    @GetMapping
    public ResponseEntity<?> getAllCategoryProduct() {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryProductById(@PathVariable("id") Integer id) {
        return null;
    }

    @PostMapping
    public ResponseEntity<?> addCategoryProduct() {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategoryProduct(@PathVariable("id") Integer id) {
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategoryProduct(@PathVariable("id") Integer id ) {
        return null;
    }
}
