package com.example.website_ban_trang_suc_toan_huyen.payload.request;


import lombok.Data;

@Data
public class SizeSearchRequest {

    private String keyword = "";

    private Integer pageIndex = 1;

    private Integer pageSize = 10;

    private String sortBy;

}
