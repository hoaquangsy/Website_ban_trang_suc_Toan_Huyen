package com.example.website_ban_trang_suc_toan_huyen.exception;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private int code;
    private String message;
}
