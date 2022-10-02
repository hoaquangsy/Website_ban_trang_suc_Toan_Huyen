package com.example.website_ban_trang_suc_toan_huyen.restController;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CategoryPropertyController {

    @GetMapping
    public ResponseEntity<?> getAllCategoryProperty() {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryPropertyById(@PathVariable("id") Integer id) {
        return null;
    }

    @PostMapping
    public ResponseEntity<?> addCategoryProperty() {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategoryProperty(@PathVariable("id") Integer id) {
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategoryProperty(@PathVariable("id") Integer id ) {
        return null;
    }
}
