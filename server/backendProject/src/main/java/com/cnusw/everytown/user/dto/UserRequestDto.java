package com.cnusw.everytown.user.dto;

import com.cnusw.everytown.user.entity.Authority;
import com.cnusw.everytown.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Slf4j
public class UserRequestDto {
    private int id;
    private String password;
    private String nickname;
    private String name;

    public User toUser(PasswordEncoder passwordEncoder) {
        return User.builder()
                .id(id)
                .password(passwordEncoder.encode(password))
                .name(name)
                .nickname(nickname)
                .email(id + "@cnu.ac.kr")
                .authority(Authority.ROLE_USER)
                .build();
    }

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(id, password);
    }
}
