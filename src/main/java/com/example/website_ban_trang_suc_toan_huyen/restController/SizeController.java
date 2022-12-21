package com.example.website_ban_trang_suc_toan_huyen.restController;


import com.example.website_ban_trang_suc_toan_huyen.payload.request.EventSearchRequest;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.SizeRequest;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.SizeSearchRequest;
import com.example.website_ban_trang_suc_toan_huyen.payload.response.SampleResponse;
import com.example.website_ban_trang_suc_toan_huyen.service.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/size")
@CrossOrigin("*")
public class SizeController {

    @Autowired
    private SizeService sizeService;

    @Autowired
    HttpServletRequest request;
    @GetMapping("/getAll")
    public ResponseEntity<?> getAllSize() {
     return ResponseEntity.ok(SampleResponse.success(this.sizeService.getAllSize()));
    }

    @GetMapping("/{id}/product")
    public ResponseEntity<?> getSizebyproductId(@PathVariable("id") UUID productId){
        return ResponseEntity.ok(SampleResponse.success(this.sizeService.getByProductId(productId)));
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
    @GetMapping()
    public ResponseEntity<?> search(SizeSearchRequest eventSearchRequest) throws ParseException {
        return ResponseEntity.ok(SampleResponse.success(this.sizeService.search(eventSearchRequest)));
    }
}
