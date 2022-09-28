package com.example.website_ban_trang_suc_toan_huyen.constant;

import lombok.Getter;

@Getter
public enum ResponseStatusConstant {
    COMMON_FAIL(0, "Hệ thống đang gặp sự cố vui lòng thử lại sau."),
    FAIL(0, "Thất bại"),
    SUCCESS(1, "Thành công."),
    FORBIDEN(403, "Bạn không có quyền truy xuất");


    private final int code;
    private final String message;

    ResponseStatusConstant(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
