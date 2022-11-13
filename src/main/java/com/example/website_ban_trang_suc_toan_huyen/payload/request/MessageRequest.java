package com.example.website_ban_trang_suc_toan_huyen.payload.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class MessageRequest {
    @NotNull
    private UUID userId;
    @NotBlank
    private String message;
    @NotNull
    private Boolean send;
    @NotBlank
    private String image;

}
