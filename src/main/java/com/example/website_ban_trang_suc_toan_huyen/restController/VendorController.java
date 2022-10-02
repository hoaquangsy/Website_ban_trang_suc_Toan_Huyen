package com.example.website_ban_trang_suc_toan_huyen.restController;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/v1/vendor")
public class VendorController {

    @GetMapping
    public ResponseEntity<?> getAllVendor() {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getVendorById(@PathVariable("id") Integer id) {
        return null;
    }

    @PostMapping
    public ResponseEntity<?> addVendor() {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVendor(@PathVariable("id") Integer id) {
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateVendor(@PathVariable("id") Integer id ) {
        return null;
    }
}
