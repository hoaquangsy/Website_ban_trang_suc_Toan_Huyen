package com.example.website_ban_trang_suc_toan_huyen.entity.dto;


import com.example.website_ban_trang_suc_toan_huyen.entity.entity.ExchangeEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
public class ExchangeDTO {

    private UUID id;

    private UUID orderId;

    private OrderDTO orderDTO;

    private ExchangeEntity.StatusEnum status;

    private String reason;

    private String note;

    private Instant createAt;

    private Instant lastmodifiedAt;

    private String createBy;

    private String lastmodifiedBy;

    private List<ProductOrderDto> productOrderDtoList;
}
