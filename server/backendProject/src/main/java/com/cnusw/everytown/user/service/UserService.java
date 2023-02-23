package com.cnusw.everytown.user.service;

import com.cnusw.everytown.config.SecurityUtil;
import com.cnusw.everytown.user.dto.UserResponseDto;
import com.cnusw.everytown.user.entity.User;
import com.cnusw.everytown.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDto getMyInfoBySecurity() {
        return userRepository.findById(SecurityUtil.getCurrentUserId())
                .map(UserResponseDto::of)
                .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다."));

    }

    @Transactional
    public UserResponseDto changeUserPassword(String exPassword, String newPassword) {
        User user = userRepository.findById(SecurityUtil.getCurrentUserId()).orElseThrow(() -> new RuntimeException("유저 정보가 없습니다"));
        if (!passwordEncoder.matches(exPassword, user.getPassword())) {
            throw new RuntimeException("비밀번호가 맞지 않습니다");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        return UserResponseDto.of(userRepository.save(user));
    }
}
