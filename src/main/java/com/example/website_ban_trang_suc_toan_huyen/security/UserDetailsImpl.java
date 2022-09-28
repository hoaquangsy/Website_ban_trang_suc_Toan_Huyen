package com.example.website_ban_trang_suc_toan_huyen.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDetailsImpl implements UserDetails {
    private Long id;
    private String username;
    private String fullName;
    private String passwordHash;
    private String phone;
    private String imageUrl;
    private String email;
    private Integer status;
    private Collection<? extends GrantedAuthority> authorities;

//    public static UserDetailsImpl build(User user){
//        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(user.getRole()));
//        return UserDetailsImpl.builder()
//                .id(user.getId())
//                .username(user.getUsername())
//                .fullName(user.getLastName() + " " + user.getFirstName())
//                .passwordHash(user.getPasswordHash())
//                .phone(user.getPhone())
//                .email(user.getEmail())
//                .imageUrl(user.getImageUrl())
//                .authorities(authorities)
//                .status(user.getStatus())
//                .build();
//    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return passwordHash;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
