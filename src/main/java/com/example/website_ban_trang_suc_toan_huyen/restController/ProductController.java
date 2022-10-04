package com.example.website_ban_trang_suc_toan_huyen.restController;



import com.example.website_ban_trang_suc_toan_huyen.entity.dto.ProductDto;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.ProductRequest;
import com.example.website_ban_trang_suc_toan_huyen.payload.response.DefaultPagingResponse;
import com.example.website_ban_trang_suc_toan_huyen.payload.response.SampleResponse;
import com.example.website_ban_trang_suc_toan_huyen.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
        description = "Product controller",
        name = "Các api về sản phẩm "
)
@RestController
@RequestMapping(value = "/api/v1/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Operation(summary = "Lấy tất cả product", description =
            "page: trang hiện tại (bắt đầu từ 0), page_size: số record trong trang hiện tại ")
    @GetMapping
    public ResponseEntity<?> getAllProduct(@RequestParam(value = "page") int page,
                                           @RequestParam(value = "page_size") int pageSize) {
        Page<ProductDto> productDtoPage = productService.getAllProduct(page,pageSize);
        return ResponseEntity.ok(DefaultPagingResponse.success(productDtoPage));
    }

    @Operation(summary = "Lấy thể loại theo Id", description = "Lấy sản phẩm theo Id")
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(SampleResponse.success(productService.getProductById(id)));
    }

    @PostMapping
    public ResponseEntity<?> addProduct(@Validated @RequestBody ProductRequest productRequest) {
        return ResponseEntity.ok(SampleResponse.success(productService.createProduct(productRequest)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") Integer productId) {
        return ResponseEntity.ok(productService.deleteProduct(productId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable("id") Integer id,
                                           @Validated @RequestBody ProductRequest productRequest) {
        return ResponseEntity.ok(SampleResponse.success(productService.updateProduct(id,productRequest)));
    }
}
