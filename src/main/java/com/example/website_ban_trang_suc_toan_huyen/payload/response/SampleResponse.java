package com.example.website_ban_trang_suc_toan_huyen.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class SampleResponse {
    private Boolean success;
    private String message;
    private Object data;
}
