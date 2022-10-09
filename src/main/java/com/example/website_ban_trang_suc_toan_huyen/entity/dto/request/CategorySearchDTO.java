package com.example.website_ban_trang_suc_toan_huyen.entity.dto.request;


import lombok.Data;

@Data
public class CategorySearchDTO {
    private String keyword;

    private String sortBy;

    protected int pageSize = 15;

    protected  int pageIndex = 1;
}
