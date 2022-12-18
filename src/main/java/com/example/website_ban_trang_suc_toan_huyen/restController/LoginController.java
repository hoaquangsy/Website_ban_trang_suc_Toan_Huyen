package com.example.website_ban_trang_suc_toan_huyen.restController;

import com.example.website_ban_trang_suc_toan_huyen.entity.dto.AuthDTO;
import com.example.website_ban_trang_suc_toan_huyen.entity.dto.RoleDTO;
import com.example.website_ban_trang_suc_toan_huyen.entity.entity.UserEntity;
import com.example.website_ban_trang_suc_toan_huyen.exception.CustomException;
import com.example.website_ban_trang_suc_toan_huyen.jwt.JwtTokenProvider;
import com.example.website_ban_trang_suc_toan_huyen.payload.request.AuthRequest;
import com.example.website_ban_trang_suc_toan_huyen.payload.response.AuthResponse;
import com.example.website_ban_trang_suc_toan_huyen.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider tokenProvider;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HttpSession session;
    @PostMapping("/login")
    public AuthResponse authenticateUser(@Valid @RequestBody AuthRequest loginRequest) throws CustomException {

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserEntity userEntity = userRepository.finUserEntitybyUsername(authentication.getName());
            String jwt = tokenProvider.createToken(authentication);
            AuthDTO authDTO = new AuthDTO();
            authDTO.setId(userEntity.getUserId());
            authDTO.setToken(jwt);
            authDTO.setUsername(userEntity.getUserName());
            List<RoleDTO> list = new ArrayList<>();
                list.add(new RoleDTO(userEntity.getRole()));
            authDTO.setRoles(list);
            this.session.setAttribute("userName",userEntity.getUserName());
            return new AuthResponse(authDTO);
        } catch (Exception e) {
            throw new CustomException(403, "Đăng nhập thất bại kiểm tra lại tài khoản mật khẩu");
        }
    }
}
