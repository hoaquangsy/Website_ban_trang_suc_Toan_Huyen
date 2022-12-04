package com.example.website_ban_trang_suc_toan_huyen.payload.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseResponse<T> {
    protected int status;

    protected String message;

    protected T data;

    public BaseResponse(T data){
        this.data = data;
    }
}
