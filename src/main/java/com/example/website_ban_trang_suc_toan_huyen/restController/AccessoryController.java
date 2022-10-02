package com.example.website_ban_trang_suc_toan_huyen.restController;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping(value = "api/v1/accessory")
public class AccessoryController {

    @GetMapping
    public ResponseEntity<?> getAllAccessory() {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAccessoryById(@PathVariable("id") Integer id) {
        return null;
    }

    @PostMapping
    public ResponseEntity<?> addAccessory() {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAccessory(@PathVariable("id") Integer id) {
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAccessory(@PathVariable("id") Integer id ) {
        return null;
    }
}
