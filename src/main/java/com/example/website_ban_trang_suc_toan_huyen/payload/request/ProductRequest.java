package com.example.website_ban_trang_suc_toan_huyen.payload.request;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
public class ProductRequest {
    @NotBlank(message = "Thể loại không được để trống")
    private UUID categoryId;
    @NotBlank(message = "Nhà cung cấp không để trống")
    private UUID vendorId;
    @NotBlank(message = "Tên sản phẩm không để trống")
    private String nameProduct;
    @NotNull(message = "Cân nặng không được để trống")
    private float weight;
    @NotNull(message = "Giá mua không được để trống")
    private BigDecimal purchasePrice;
    @NotNull(message = "Giá bán không được để trống")
    private BigDecimal salePrice;
    private String note;
    @NotNull(message = "Lương không để trống")
    private BigDecimal salary;
    @NotBlank(message = "Phụ kiện không để trống")
    private String accessoryId;
    @NotBlank(message = "Giới tính không để trống")
    private String gender;
    @NotEmpty(message = "Size không để trống")
    private List<SizeProduct> sizeProducts;
    private UUID eventId;
    @NotBlank(message = "Nguyên liệu không để trống")
    private UUID materialId;
    private List<ProductProperty> productProperty;
    private List<String> imageUrls;

}
