package com.example.website_ban_trang_suc_toan_huyen.payload.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SamplePagingResponse {
    private Boolean success;
    private String message;
    private Object data;
    private Pagination pagination;
}