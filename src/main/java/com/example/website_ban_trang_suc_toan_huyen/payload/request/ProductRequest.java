package com.example.website_ban_trang_suc_toan_huyen.payload.request;

import com.example.website_ban_trang_suc_toan_huyen.entity.dto.ProductStatistical;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.ProductEntity;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
public class ProductRequest {
    @NotNull(message = "Thể loại không được để trống")
    private UUID categoryId;

    @NotNull(message = "Nhà cung cấp không để trống")
    private UUID vendorId;

    @NotBlank(message = "Tên sản phẩm không để trống")
    private String nameProduct;

    private String note;

    private ProductEntity.StatusEnum status;

    @NotNull(message = "Lương không để trống")
    private BigDecimal salary;
    @NotNull(message = "Phụ kiện không để trống")
    private UUID accessoryId;
    @NotNull(message = "Giới tính không để trống")
    private ProductEntity.ProductGender gender;
    @NotEmpty(message = "Size không để trống")
    private List<SizeProduct> sizeProducts;
    private UUID eventId;
    @NotNull(message = "Nguyên liệu không để trống")
    private UUID materialId;

    private List<ProductProperty> productProperties;

    private List<String> imageUrls;
}
