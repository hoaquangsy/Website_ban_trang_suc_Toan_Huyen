package com.example.website_ban_trang_suc_toan_huyen.restController;



import com.example.website_ban_trang_suc_toan_huyen.entity.dto.ProductDto;
import com.example.website_ban_trang_suc_toan_huyen.entity.dto.response.PageDTO;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.ProductEntity;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.ProductRequest;
import com.example.website_ban_trang_suc_toan_huyen.payload.response.DefaultPagingResponse;
import com.example.website_ban_trang_suc_toan_huyen.payload.response.SampleResponse;
import com.example.website_ban_trang_suc_toan_huyen.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@Tag(
        description = "Product controller",
        name = "Các api về sản phẩm "
)
@RestController
@Slf4j
@RequestMapping(value = "/api/v1/product")
@CrossOrigin("*")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Operation(summary = "Lấy tất cả product", description =
            "page: trang hiện tại (bắt đầu từ 0), page_size: số record trong trang hiện tại ")
    @GetMapping("/getAll")
    public ResponseEntity<?> getAllProduct(@RequestParam(value = "page") int page,
                                           @RequestParam(value = "page_size") int pageSize) {
        Page<ProductDto> productDtoPage = productService.getAllProduct(page,pageSize);
        return ResponseEntity.ok(DefaultPagingResponse.success(productDtoPage));
    }

    @Operation(summary = "Lấy thể loại theo Id", description = "Lấy sản phẩm theo Id")
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(SampleResponse.success(productService.getProductById(id)));
    }
    @Operation(summary = "Lấy thể loại theo Id", description = "Lấy sản phẩm theo Id")
    @GetMapping("/trending")
    public ResponseEntity<?> getProductTrending() {
        return ResponseEntity.ok(SampleResponse.success(productService.getProductTrending()));
    }

    @PostMapping
    public ResponseEntity<?> addProduct(@Validated @RequestBody ProductRequest productRequest) {
        return ResponseEntity.ok(SampleResponse.success(productService.createProduct(productRequest)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") UUID productId) {
        return ResponseEntity.ok(productService.deleteProduct(productId));
    }

    @GetMapping("/{id}/lock")
    public ResponseEntity<?> lock(@PathVariable("id") UUID productId) {
        return ResponseEntity.ok(productService.lock(productId));
    }
    @GetMapping("/{id}/unlock")
    public ResponseEntity<?> unlock(@PathVariable("id") UUID productId) {
        return ResponseEntity.ok(productService.unlock(productId));
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable("id") UUID id,
                                           @Validated @RequestBody ProductRequest productRequest) {
        return ResponseEntity.ok(SampleResponse.success(productService.updateProduct(id,productRequest)));
    }
    @GetMapping()
    public PageDTO search(@RequestParam(value = "pageIndex",defaultValue = "1",required = false) Integer pageIndex,
                          @RequestParam(value = "pageSize",defaultValue = "10",required = false) Integer pageSize,
                          @RequestParam(value = "keyword",defaultValue = "",required = false) String keyword,
                          @RequestParam(value = "status",required = false) ProductEntity.StatusEnum status,
                          @RequestParam(value = "materialId",required = false) UUID materialId,
                          @RequestParam(value = "vendorId",required = false) UUID vendorId,
                          @RequestParam(value = "categoryId",required = false) UUID categoryId,
                          @RequestParam(value = "accessoryId",required = false) UUID accessoryId,
                          @RequestParam(value = "startPrice",required = false) BigDecimal startPrice,
                          @RequestParam(value = "endPrice",required = false) BigDecimal endPrice,
                          @RequestParam(value = "sortBy",required = false,defaultValue = "") String sortBy,
                          @RequestParam(value = "gender",required = false) ProductEntity.ProductGender gender){
        return this.productService.search(pageIndex,pageSize,keyword,status,materialId,vendorId,accessoryId,categoryId,startPrice,endPrice,sortBy,gender);

    }
    @GetMapping("autoComplete")
    public PageDTO autocomplete(@RequestParam(value = "pageIndex",defaultValue = "1",required = false) Integer pageIndex,
                          @RequestParam(value = "pageSize",defaultValue = "10",required = false) Integer pageSize,
                          @RequestParam(value = "keyword",defaultValue = "",required = false) String keyword,
                          @RequestParam(value = "status",required = false) ProductEntity.StatusEnum status,
                          @RequestParam(value = "materialId",required = false) UUID materialId,
                          @RequestParam(value = "vendorId",required = false) UUID vendorId,
                          @RequestParam(value = "categoryId",required = false) UUID categoryId,
                          @RequestParam(value = "accessoryId",required = false) UUID accessoryId,
                          @RequestParam(value = "startPrice",required = false) BigDecimal startPrice,
                          @RequestParam(value = "endPrice",required = false) BigDecimal endPrice,
                          @RequestParam(value = "sortBy",required = false,defaultValue = "") String sortBy,
                          @RequestParam(value = "gender",required = false) ProductEntity.ProductGender gender){
        return this.productService.autoComplete(pageIndex,pageSize,keyword,status,materialId,vendorId,accessoryId,categoryId,startPrice,endPrice,sortBy,gender);

    }
    @GetMapping("product-order")
    public ResponseEntity<?> getProductOrder(){
        return ResponseEntity.ok(SampleResponse.success(this.productService.getProductOrder()));
    }
}
