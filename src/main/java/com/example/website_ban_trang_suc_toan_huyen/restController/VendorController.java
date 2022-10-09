package com.example.website_ban_trang_suc_toan_huyen.restController;


import com.example.website_ban_trang_suc_toan_huyen.entity.dto.CategoryDto;
import com.example.website_ban_trang_suc_toan_huyen.entity.dto.VendorDto;
import com.example.website_ban_trang_suc_toan_huyen.entity.dto.response.PageDTO;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.VendorRequest;
import com.example.website_ban_trang_suc_toan_huyen.payload.response.DefaultPagingResponse;
import com.example.website_ban_trang_suc_toan_huyen.payload.response.SampleResponse;
import com.example.website_ban_trang_suc_toan_huyen.service.VendorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(
        description = "Vendor controller",
        name = "Các api về nhà cung cấp "
)
@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/vendor")
@Slf4j
public class VendorController {
    @Autowired
    private VendorService vendorService;

    @Operation(summary = "Lấy tất cả vendor", description =
            "page: trang hiện tại (bắt đầu từ 0), page_size: số record trong trang hiện tại ")
    @GetMapping
    public ResponseEntity<?> getAllVendor(@RequestParam(value = "pageIndex",required = false,defaultValue = "1") Integer pageNumber,
                                          @RequestParam(value = "pageSize",required = false,defaultValue = "15") Integer pageSize,
                                          @RequestParam(value = "keyword",required = false,defaultValue = "") String keyword,
                                          @RequestParam(value = "sortBy",required = false) String sortBy) {
        PageDTO vendorDtoPage= vendorService.getAllVendor(pageNumber,pageSize , keyword, sortBy);
        return ResponseEntity.ok(SampleResponse.success(vendorDtoPage));
    }

    @Operation(summary = "Lấy thể loại theo Id", description = "Lấy nhà cung cấp theo Id")
    @GetMapping("/{id}")
    public ResponseEntity<?> getVendorById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(SampleResponse.success(vendorService.getVendorById(id)));
    }

    @PostMapping
    public ResponseEntity<?> addVendor( @RequestBody VendorRequest vendorRequest) {
        log.info("vendorRequest"+vendorRequest);
        return ResponseEntity.ok(SampleResponse.success(vendorService.createVendor(vendorRequest)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVendor(@PathVariable("id") UUID id) {

        return ResponseEntity.ok(SampleResponse.success(vendorService.deleteVendor(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateVendor(@PathVariable("id") UUID id, @Validated @RequestBody VendorRequest vendorRequest) {
        return ResponseEntity.ok(SampleResponse.success(vendorService.updateVendor(id, vendorRequest)));
    }
}
