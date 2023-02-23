package com.cnusw.everytown.user.service;

import com.cnusw.everytown.user.dto.TokenDto;
import com.cnusw.everytown.user.dto.UserRequestDto;
import com.cnusw.everytown.user.dto.UserResponseDto;
import com.cnusw.everytown.user.entity.User;
import com.cnusw.everytown.user.jwt.TokenProvider;
import com.cnusw.everytown.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AuthService {
    private final AuthenticationManagerBuilder managerBuilder;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    @Transactional
    public UserResponseDto signup(UserRequestDto requestDto) {
        if (userRepository.existsById(requestDto.getId())) {
            throw new RuntimeException("이미 가입된 유저입니다.");
        }

        User user = requestDto.toUser(passwordEncoder);
        return UserResponseDto.of(userRepository.save(user));
    }

    @Transactional
    public TokenDto login(UserRequestDto requestDto) {
        // 사용자가 입력한 login id, password로 usernamepasswordauthenticationtoken 생성
        UsernamePasswordAuthenticationToken authenticationToken = requestDto.toAuthentication();
        // managebuilder에서 검증
        Authentication authentication = managerBuilder.getObject().authenticate(authenticationToken);
        return tokenProvider.generateTokenDto(authentication);
    }
}
