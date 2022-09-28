package com.example.website_ban_trang_suc_toan_huyen.restController;


import com.example.website_ban_trang_suc_toan_huyen.entity.dto.CategoryDto;
import com.example.website_ban_trang_suc_toan_huyen.payload.response.DefaultPagingResponse;
import com.example.website_ban_trang_suc_toan_huyen.payload.response.SampleResponse;
import com.example.website_ban_trang_suc_toan_huyen.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
@Tag(
        description = "Category controller",
        name = "Các api về thể loại "
)
@RequestMapping("/api/v1/category")
@RestController
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @Operation(summary = "Lấy tất cả category", description =
            "page: trang hiện tại (bắt đầu từ 0), page_size: số record trong trang hiện tại ")
    @GetMapping
    public ResponseEntity<?> getAllCategory(@RequestParam(value = "page") int page,
                                    @RequestParam(value = "page_size") int pageSize) {
        Page<CategoryDto> categoryDtoPage=categoryService.getAllCategory(page,pageSize);
        return ResponseEntity.ok(DefaultPagingResponse.success(categoryDtoPage));
    }

    @Operation(summary = "Lấy thể loại theo Id", description = "Lấy thể loại theo Id")
    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable("id") String id) {
        return ResponseEntity.ok(SampleResponse.success(categoryService.getCategoryById(id)));
    }

    @PostMapping
    public ResponseEntity<?> createCategory(@Validated @RequestBody CategoryDto categoryDto) {
        categoryDto.setCategoryId(null);
        return ResponseEntity.ok(SampleResponse.success(categoryService.createCategory(categoryDto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") String id,
                                    @Validated @RequestBody CategoryDto categoryDto) {
        return ResponseEntity.ok(SampleResponse.success(categoryService.updateCategory(id,categoryDto)));
    }
}
