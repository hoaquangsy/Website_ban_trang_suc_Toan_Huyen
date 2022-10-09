package com.example.website_ban_trang_suc_toan_huyen.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.sql.Date;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ProductRequest {
    @NotBlank(message = "trong")
    @JsonProperty("category_id")
    private String categoryId;
    @JsonProperty("vendor_id")
    @NotBlank(message = "trong")
    private String vendorId;
    @NotBlank(message = "trong")
    private String code;
    @JsonProperty("name_Product")
    @NotBlank(message = "trong")
    private String nameProduct;
    private float weight;
    @JsonProperty("purchase_Price")
    private BigDecimal purchasePrice;
    private BigDecimal saleprice;
    private String status;
    private String note;
    @JsonProperty("create_At")
    private Date createAt;
    @JsonProperty("last_Modified_At")
    private Date lastModifiedAt;
    @JsonProperty("last_Modified_By")
    private String lastModifiedBy;
    private BigDecimal salary;
    @JsonProperty("accessory_Id")
    private String accessoryId;
    private String gender;
    private Integer sizeId;
    private Integer quantity;
//    private List<ProductProperty> productProperty;
//    private List<ImageProduct> imageProducts;
//    public static class ProductProperty{
//        private String productPropertyId;
//        private String name;  //mau
//        private String value; // xanh
//        private String productId;
//    };
//    private static class ImageProduct{
//        private String url;
//    };

}
