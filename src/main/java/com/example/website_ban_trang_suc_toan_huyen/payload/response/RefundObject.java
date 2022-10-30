package com.example.website_ban_trang_suc_toan_huyen.payload.response;

import com.example.website_ban_trang_suc_toan_huyen.constant.ResponseStatusConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefundObject<T> {
    private int status;
    private String message;
    private Object data;

    public static <T> RefundObject<T> success(T body) {
        RefundObject<T> response = new RefundObject<>();
        response.setStatus(ResponseStatusConstant.SUCCESS.getCode());
        response.setMessage(ResponseStatusConstant.SUCCESS.getMessage());
        response.setData(body);
        return response;
    }
    public static <T> RefundObject<T> error(Exception e) {
        RefundObject<T> response = new RefundObject<>();
        response.setStatus(ResponseStatusConstant.FAIL.getCode());
        response.setMessage(e.getMessage());
        response.setData(null);
        return response;
    }
}
