package com.example.website_ban_trang_suc_toan_huyen.exception;

import com.example.website_ban_trang_suc_toan_huyen.constant.ResponseStatusConstant;

public class ObjectNotFoundException extends ProjectException {

    public ObjectNotFoundException(ResponseStatusConstant e) {
        super(e.getCode(), e.getMessage());
    }
}
