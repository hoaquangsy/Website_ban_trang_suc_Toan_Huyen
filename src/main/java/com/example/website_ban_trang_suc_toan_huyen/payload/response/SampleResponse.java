package com.example.website_ban_trang_suc_toan_huyen.payload.response;

import com.example.website_ban_trang_suc_toan_huyen.constant.ResponseStatusConstant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
public class SampleResponse<T> {
    private Integer status;
    private String message;
    private T data;

    public static <T> SampleResponse<T> success(T body) {
        SampleResponse<T> response = new SampleResponse<>();
        response.setStatus(ResponseStatusConstant.SUCCESS.getCode());
        response.setMessage(ResponseStatusConstant.SUCCESS.getMessage());
        response.setData(body);
        return response;
    }
}
