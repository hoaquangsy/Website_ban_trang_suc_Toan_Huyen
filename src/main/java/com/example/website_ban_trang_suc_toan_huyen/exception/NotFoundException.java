package com.example.website_ban_trang_suc_toan_huyen.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotFoundException extends ProjectException{

    public NotFoundException(int code, String message){
        super(code,message);
    }
}