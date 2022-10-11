package com.example.website_ban_trang_suc_toan_huyen.restController;


import com.example.website_ban_trang_suc_toan_huyen.entity.dto.response.PageDTO;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.MaterialEntity;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.MaterialRequest;
import com.example.website_ban_trang_suc_toan_huyen.payload.response.SampleResponse;
import com.example.website_ban_trang_suc_toan_huyen.service.MaterialService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/material")
@CrossOrigin("*")
public class MaterialController {

    @Autowired
    private MaterialService materialService;
    @Operation(summary = "Search Chất liệu")
    @GetMapping
    public PageDTO search(@RequestParam(value = "pageIndex",defaultValue = "1",required = false) Integer pageIndex,
                          @RequestParam(value = "pageSize",defaultValue = "10",required = false) Integer pageSize,
                          @RequestParam(value = "keyword",defaultValue = "",required = false) String keyword,
                          @RequestParam(value = "status",required = false) MaterialEntity.StatusEnum status,
                          @RequestParam(value = "type",required = false) MaterialEntity.MaterialType type,
                          @RequestParam(value = "startPrice",required = false) BigDecimal startPrice,
                          @RequestParam(value = "endPrice",required = false) BigDecimal endPrice,
                          @RequestParam(value = "sortBy",required = false) String sortBy)
    {
        return this.materialService.search(keyword,pageIndex,pageSize,type,status,startPrice,endPrice,sortBy);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMaterialById(@PathVariable("id") Integer id) {
        return null;
    }

    @PostMapping
    public ResponseEntity<?> addMaterial(@Validated @RequestBody MaterialRequest request) {
        return ResponseEntity.ok(SampleResponse.success(this.materialService.createMaterial(request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMaterial(@PathVariable("id") UUID id) {
        this.materialService.deleteMaterial(id);
        return ResponseEntity.ok(SampleResponse.success(null));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMaterial(@PathVariable("id") UUID id, @Valid @RequestBody MaterialRequest request) {

        return ResponseEntity.ok(SampleResponse.success(this.materialService.updateMaterial(request,id)));
    }
}
