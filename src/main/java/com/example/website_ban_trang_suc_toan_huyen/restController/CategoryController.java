package com.example.website_ban_trang_suc_toan_huyen.restController;


import com.example.website_ban_trang_suc_toan_huyen.entity.dto.response.PageDTO;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.CategoryRequest;
import com.example.website_ban_trang_suc_toan_huyen.payload.response.SampleResponse;
import com.example.website_ban_trang_suc_toan_huyen.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(
        description = "Category controller",
        name = "Các api về thể loại "
)
@RequestMapping("/api/v1/category")
@RestController
@CrossOrigin("*")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @Operation(summary = "Lấy tất cả category", description =
            "page: trang hiện tại (bắt đầu từ 0), page_size: số record trong trang hiện tại ")
    @GetMapping
    public ResponseEntity<?> getAllCategory(@RequestParam(value = "pageIndex", required = false,defaultValue = "1") Integer pageIndex,
                                            @RequestParam(value = "pageSize",required = false,defaultValue = "10") Integer pageSize,
                                            @RequestParam(value = "keyword",required = false,defaultValue = "") String keyword,
                                            @RequestParam(value = "sortBy",required = false) String sortBy
                                            ) {
            PageDTO categoryDtoPage=categoryService.search(pageIndex,pageSize,keyword,sortBy);
        return ResponseEntity.ok(categoryDtoPage);
    }

    @Operation(summary = "Lấy thể loại theo Id", description = "Lấy thể loại theo Id")
    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable("id") UUID id) {
        System.out.println("-------Get By ID----------");
        System.out.println(this.categoryService.getCategoryById(id));
        return ResponseEntity.ok(SampleResponse.success(categoryService.getCategoryById(id)));
    }

    @PostMapping
    public ResponseEntity<?> createCategory(@Validated @RequestBody CategoryRequest categoryDto) {
        return ResponseEntity.ok(SampleResponse.success(categoryService.createCategory(categoryDto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(SampleResponse.success(this.categoryService.delete(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable("id") UUID id,
                                            @Validated @RequestBody CategoryRequest categoryDto) {
        return ResponseEntity.ok(SampleResponse.success(categoryService.updateCategory(id, categoryDto)));
    }

    @Operation(summary = "Lấy tất cả category", description =
            "page: trang hiện tại (bắt đầu từ 0), page_size: số record trong trang hiện tại ")
    @GetMapping("/auto-complete")
    public ResponseEntity<?> autoComplete(@RequestParam(value = "pageIndex", required = false, defaultValue = "1") Integer pageIndex,
                                          @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                          @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
                                          @RequestParam(value = "sortBy", required = false) String sortBy
    ) {
        PageDTO categoryDtoPage = categoryService.autoComplete(pageIndex, pageSize, keyword, sortBy);
        return ResponseEntity.ok(categoryDtoPage);
    }

    @GetMapping("{id}/properties")
    public ResponseEntity<?> getProperties(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(SampleResponse.success(categoryService.getProperties(id)));
    }
}
