package com.example.website_ban_trang_suc_toan_huyen.payload.request;

import com.example.website_ban_trang_suc_toan_huyen.entity.entity.OrderEntity;
import lombok.Data;

import javax.validation.constraints.NotNull;
@Data
public class OrderUpdate {

    @NotNull
    private OrderEntity.StatusEnum status;
}
