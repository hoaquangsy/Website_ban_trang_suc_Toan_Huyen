package com.example.website_ban_trang_suc_toan_huyen.restController;



import com.example.website_ban_trang_suc_toan_huyen.dao.ProductDao;
import com.example.website_ban_trang_suc_toan_huyen.entity.dto.ProductDto;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.ProductEntity;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.ProductRequest;
import com.example.website_ban_trang_suc_toan_huyen.payload.response.DefaultPagingResponse;
import com.example.website_ban_trang_suc_toan_huyen.payload.response.SampleResponse;
import com.example.website_ban_trang_suc_toan_huyen.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Tag(
        description = "Product controller",
        name = "Các api về sản phẩm "
)
@RestController
@Slf4j
@RequestMapping(value = "/api/v1/product")
public class ProductController {
    private ProductDao productDao;
    ProductController() throws SQLException{
        this.productDao = new ProductDao();
    }
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
    public ResponseEntity<?> getProductById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(SampleResponse.success(productService.getProductById(id)));
    }

    @PostMapping
    public ResponseEntity<?> addProduct(@Validated @RequestBody ProductRequest productRequest) {
        return ResponseEntity.ok(SampleResponse.success(productService.createProduct(productRequest)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") UUID productId) {
        return ResponseEntity.ok(productService.deleteProduct(productId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable("id") UUID id,
                                           @Validated @RequestBody ProductRequest productRequest) {
        return ResponseEntity.ok(SampleResponse.success(productService.updateProduct(id,productRequest)));
    }
    @PostMapping("/import-excel")
    public void importExcel(@RequestBody MultipartFile file) throws SQLException, IOException {
        Long start = System.currentTimeMillis();
        List<ProductEntity> product = new ArrayList<ProductEntity>();
        InputStream input = file.getInputStream();
        XSSFWorkbook workbook = new XSSFWorkbook(input);
        XSSFSheet sheet = workbook.getSheetAt(0);
        PreparedStatement preparedStatement = null;
        for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
            ProductEntity product1 = new ProductEntity();
            XSSFRow row = sheet.getRow(i);
            product1.setCategoryId(UUID.fromString(row.getCell(0).getStringCellValue()));
            product1.setVendorId(UUID.fromString(row.getCell(1).getStringCellValue()));
            product1.setCode(row.getCell(2).getStringCellValue());
            product1.setNameProduct(row.getCell(3).getStringCellValue());
            product1.setWeight((float) row.getCell(4).getNumericCellValue());
            product1.setPurchasePrice(BigDecimal.valueOf(row.getCell(5).getNumericCellValue()));
            product1.setSalePrice(BigDecimal.valueOf(row.getCell(6).getNumericCellValue()));
            product1.setNote(row.getCell(7).getStringCellValue());
            product1.setSalary(BigDecimal.valueOf(row.getCell(8).getNumericCellValue()));
            product1.setAccessoryId(row.getCell(9).getStringCellValue());
            product1.setGender(row.getCell(10).getStringCellValue());
            product1.setEventId(UUID.fromString(row.getCell(11).getStringCellValue()));
            product1.setMaterialId(UUID.fromString(row.getCell(12).getStringCellValue()));



            preparedStatement = productDao.saveProduct(product1);
            preparedStatement.addBatch();
            if (i % 1000 == 0) {
                preparedStatement.executeBatch();
                preparedStatement.clearBatch();
            }
        }
        if (preparedStatement != null) {
            preparedStatement.executeBatch();
            preparedStatement.clearBatch();
        }
        System.out.println("Import excel success! Time: " + (System.currentTimeMillis() - start) + " ms");
    }

}
