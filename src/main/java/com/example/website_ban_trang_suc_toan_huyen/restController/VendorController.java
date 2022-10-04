package com.example.website_ban_trang_suc_toan_huyen.restController;


import com.example.website_ban_trang_suc_toan_huyen.payload.request.VendorRequest;
import com.example.website_ban_trang_suc_toan_huyen.payload.response.SampleResponse;
import com.example.website_ban_trang_suc_toan_huyen.service.VendorService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
@Tag(
        description = "Vendor controller",
        name = "Các api về nhà cung cấp "
)
@RestController
@RequestMapping("/api/v1/vendor")
public class VendorController {
    @Autowired
    private VendorService vendorService;

    @GetMapping
    public ResponseEntity<?> getAllVendor() {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getVendorById(@PathVariable("id") Integer id) {
        return null;
    }

    @PostMapping
    public ResponseEntity<?> addVendor(@Validated @RequestBody VendorRequest vendorRequest) {
        return ResponseEntity.ok(SampleResponse.success(vendorService.createVendor(vendorRequest)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVendor(@PathVariable("id") Integer id) {
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateVendor(@PathVariable("id") Integer id) {
        return null;
    }
}
