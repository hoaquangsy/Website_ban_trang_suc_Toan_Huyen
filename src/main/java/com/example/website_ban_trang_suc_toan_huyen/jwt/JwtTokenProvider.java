package com.example.website_ban_trang_suc_toan_huyen.jwt;

import com.example.website_ban_trang_suc_toan_huyen.jwt.user.CustomUserDetails;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class JwtTokenProvider {
    // Đoạn JWT_SECRET này là bí mật, chỉ có phía server biết
    private final String JWT_SECRET = "nangdz";

    //Thời gian có hiệu lực của chuỗi jwt
    private final int JWT_EXPIRATION = 86400;

    public String createToken(Authentication authentication){
        CustomUserDetails userPrinciple = (CustomUserDetails) authentication.getPrincipal();
        return Jwts.builder().setSubject(userPrinciple.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime()+JWT_EXPIRATION*1000))
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }
    public String generateJwtToken(CustomUserDetails userDetails) {
        return Jwts.builder()
                .setSubject((userDetails.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime()+JWT_EXPIRATION*1000))
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }

    // Lấy thông tin user từ jwt
    public Long getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getSubject());
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException ex) {
            log.error("Thông báo JWT không hợp lệ");
        } catch (ExpiredJwtException ex) {
            log.error("Thông báo JWT đã hết hạn");
        } catch (UnsupportedJwtException ex) {
            log.error("Thông báo JWT không được hỗ trợ");
        } catch (IllegalArgumentException ex) {
            log.error("Chuỗi yêu cầu JWT trống.");
        }
        return false;
    }
    public String getUerNameFromToken(String token){
        String userName = Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody().getSubject();
        return userName;
    }
}
