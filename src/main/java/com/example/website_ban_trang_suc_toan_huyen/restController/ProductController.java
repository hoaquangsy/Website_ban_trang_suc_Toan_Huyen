package com.example.website_ban_trang_suc_toan_huyen.restController;



import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/product")
@RestController
public class ProductController {

    @GetMapping
    public ResponseEntity<?> getAllProduct() { return null;
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable("id") String id) {

        return null;
    }

    @PostMapping
    public ResponseEntity<?> addProduct() {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") Integer id) {
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable("id") Integer id ) {
        return null;
    }
}
