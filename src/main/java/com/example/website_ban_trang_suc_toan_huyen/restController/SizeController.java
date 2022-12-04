package com.example.website_ban_trang_suc_toan_huyen.restController;


import com.example.website_ban_trang_suc_toan_huyen.payload.request.SizeRequest;
import com.example.website_ban_trang_suc_toan_huyen.payload.response.SampleResponse;
import com.example.website_ban_trang_suc_toan_huyen.service.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/size")
@CrossOrigin("*")
public class SizeController {

    @Autowired
    private SizeService sizeService;

    @Autowired
    HttpServletRequest request;
    @GetMapping
    public ResponseEntity<?> getAllSize() {
     return ResponseEntity.ok(SampleResponse.success(this.sizeService.getAllSize()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSizeById(@PathVariable("id") UUID id) {

      return ResponseEntity.ok(SampleResponse.success(this.sizeService.getById(id)));
    }

    @PostMapping
    public ResponseEntity<?> addSize(@Validated @RequestBody SizeRequest sizeRequest)
    {
        return ResponseEntity.ok(SampleResponse.success(this.sizeService.create(sizeRequest)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSize(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(SampleResponse.success(this.sizeService.delete(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSize(@PathVariable("id") UUID id, @Validated @RequestBody SizeRequest sizeRequest) {
        return ResponseEntity.ok(SampleResponse.success(this.sizeService.update(id,sizeRequest)));
    }
}
