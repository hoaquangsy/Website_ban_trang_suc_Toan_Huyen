package com.example.website_ban_trang_suc_toan_huyen.jwt.user;

import com.example.website_ban_trang_suc_toan_huyen.entity.entity.UserEntity;
import com.example.website_ban_trang_suc_toan_huyen.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service("userServiceDetails")
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.finUserEntitybyUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_"+user.getRole().toString()));

        CustomUserDetails userDetails = new CustomUserDetails(user.getUserName(), user.getPassword(), authorities);
        BeanUtils.copyProperties(user, userDetails);
        return userDetails;
    }

    @Transactional
    public UserDetails loadUserById(UUID id) {
        UserEntity user = userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("Không tìm thấy người có id : " + id)
        );

        return CustomUserDetails.build(user);
    }
}
