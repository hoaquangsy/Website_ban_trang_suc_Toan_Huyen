package com.example.website_ban_trang_suc_toan_huyen.payload.request;

import com.example.website_ban_trang_suc_toan_huyen.entity.entity.ExchangeEntity;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Data
//@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ExchangeRequest {
    @NotNull(message = "Hóa đơn không để trống")
    private UUID orderId;
    @NotEmpty(message = "Sản phẩm  không để trống")
    private List<ExchangeDetail> products;
    private String note;
    @NotBlank(message = "Hóa đơn không để trống")
    private String reason;
    @NotNull(message = "Trang thái không để trống")
    private ExchangeEntity.StatusEnum status;

    @Data
    public static class ExchangeDetail{
      private UUID productId;

      private UUID sizeId;

      private Integer quantity;
    }
}
