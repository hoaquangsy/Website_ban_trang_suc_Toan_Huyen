package com.example.website_ban_trang_suc_toan_huyen.restController;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SizeController {

    @GetMapping
    public ResponseEntity<?> getAllSize() {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSizeById(@PathVariable("id") Integer id) {
        return null;
    }

    @PostMapping
    public ResponseEntity<?> addSize() {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSize(@PathVariable("id") Integer id) {
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSize(@PathVariable("id") Integer id ) {
        return null;
    }
}
