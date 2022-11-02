package com.example.website_ban_trang_suc_toan_huyen.restController;


import com.example.website_ban_trang_suc_toan_huyen.entity.dto.AccessoryDTO;
import com.example.website_ban_trang_suc_toan_huyen.entity.dto.response.PageDTO;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.AccessoryRequest;
import com.example.website_ban_trang_suc_toan_huyen.payload.response.SampleResponse;
import com.example.website_ban_trang_suc_toan_huyen.service.AccessoryService;
import com.example.website_ban_trang_suc_toan_huyen.support.enums.AccessoryStatus;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;


@RestController()
@RequestMapping(value = "api/v1/accessory")
@CrossOrigin("*")
public class AccessoryController {

    @Autowired
    private AccessoryService accessoryService;

    @Operation(summary = "Get accessory by id")
    @GetMapping("/{id}")
    public ResponseEntity<?> getAccessoryById(@PathVariable("id") UUID id) {
        AccessoryDTO accessoryDTO=accessoryService.getById(id);
        return ResponseEntity.ok(SampleResponse.success(accessoryDTO));
    }

    @PostMapping
    public ResponseEntity<?> createAccessory(@Validated @RequestBody AccessoryRequest request) {
        return ResponseEntity.ok(SampleResponse.success(accessoryService.create(request)));
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(SampleResponse.success(accessoryService.delete(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") UUID id ,
                                    @Validated @RequestBody AccessoryRequest request) {
        return ResponseEntity.ok(SampleResponse.success(accessoryService.update(request,id)));
    }

    @Operation(summary = "Search accessory")
    @GetMapping
    public PageDTO search(@RequestParam(value = "pageIndex",defaultValue = "1",required = false) Integer page,
                                            @RequestParam(value = "pageSize",defaultValue = "10",required = false) Integer pageSize,
                                            @RequestParam(value = "keyword",defaultValue = "",required = false) String keyword,
                                            @RequestParam(value = "status",required = false) AccessoryStatus status,
                                            @RequestParam(value = "sortBy",required = false) String sortBy
    ) {
        return this.accessoryService.search(keyword,page,pageSize,status,sortBy);
    }

    @PutMapping("active/{id}")
    public ResponseEntity<?> active(@PathVariable("id") UUID id ) {
        return ResponseEntity.ok(SampleResponse.success(accessoryService.active(id)));
    }

    @PutMapping("inactive/{id}")
    public ResponseEntity<?> inactive(@PathVariable("id") UUID id ) {
        return ResponseEntity.ok(SampleResponse.success(accessoryService.inactive(id)));
    }

    @PutMapping("drafts/{id}")
    public ResponseEntity<?> drafts(@PathVariable("id") UUID id ) {
        return ResponseEntity.ok(SampleResponse.success(accessoryService.draft(id)));
    }
    @Operation(summary = "auto complete")
    @GetMapping("/auto-complete")
    public ResponseEntity<?> autoComplete(@RequestParam(value = "pageIndex",defaultValue = "1",required = false) Integer page,
                                          @RequestParam(value = "pageSize",defaultValue = "10",required = false) Integer pageSize,
                                          @RequestParam(value = "keyword",defaultValue = "",required = false) String keyword,
                                          @RequestParam(value = "status",required = false) AccessoryStatus status,
                                          @RequestParam(value = "startPrice",required = false) BigDecimal startPrice,
                                          @RequestParam(value = "endPrice",required = false) BigDecimal endPrice,
                                          @RequestParam(value = "sortBy",required = false) String sortBy) {
        return ResponseEntity.ok(SampleResponse.success(accessoryService.autoComplete(keyword,page,pageSize,status,sortBy)));
    }



}
