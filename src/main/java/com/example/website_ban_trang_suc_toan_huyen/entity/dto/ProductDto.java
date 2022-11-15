package com.example.website_ban_trang_suc_toan_huyen.entity.dto;

import com.example.website_ban_trang_suc_toan_huyen.entity.entity.ProductEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
public class ProductDto {

    private UUID productId;

    private UUID categoryId;

    private CategoryDto category;

    private UUID vendorId;

    private VendorDto vendor;

    private String code;

    private String nameProduct;

    private UUID materialId;

    private MaterialDto material;

    private String status;

    private String note;

    private BigDecimal salary;

    private UUID accessoryId;

    private UUID eventID;

    private AccessoryDTO accessory;

    private ProductEntity.ProductGender gender;

    private Instant createAt;

    private Instant lastModifiedAt;

    private String lastModifiedBy;

    private List<ProductImageDTO> productImages;

    private List<ProductPropertyDto> productProperties;

    private List<ProductSizeDto> productSizes;

}
