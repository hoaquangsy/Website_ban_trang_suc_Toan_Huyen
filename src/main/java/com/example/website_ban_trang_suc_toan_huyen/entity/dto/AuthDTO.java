package com.example.website_ban_trang_suc_toan_huyen.entity.dto;

import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AuthDTO implements Serializable {
    private UUID id;
    private String token;
    private List<RoleDTO> roles;
    private String username;
}
