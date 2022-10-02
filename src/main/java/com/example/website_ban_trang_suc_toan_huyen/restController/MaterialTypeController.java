package com.example.website_ban_trang_suc_toan_huyen.restController;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/materialtype")

public class MaterialTypeController {

    @GetMapping
    public ResponseEntity<?> getAllMaterialType() {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMaterialTypeById(@PathVariable("id") Integer id) {
        return null;
    }

    @PostMapping
    public ResponseEntity<?> addMaterialType() {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMaterialType(@PathVariable("id") Integer id) {
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMaterialType(@PathVariable("id") Integer id ) {
        return null;
    }
}
