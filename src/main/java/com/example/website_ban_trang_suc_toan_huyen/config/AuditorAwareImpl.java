package com.example.website_ban_trang_suc_toan_huyen.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import java.util.Optional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Autowired
    HttpServletRequest request;
    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication auth = SecurityContextHolder
                .getContext()
                .getAuthentication();
        if (auth == null) {
            return Optional.of("duc");
        }
        if(auth.getPrincipal() instanceof  String){
            return Optional.ofNullable(auth.getPrincipal().toString());
        }

        String userName = this.request.getSession().getAttribute("userName")+"";

        return Optional.of(userName);
    }

}