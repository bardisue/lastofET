package com.cnusw.everytown.user.controller;

import com.cnusw.everytown.user.dto.TokenDto;
import com.cnusw.everytown.user.dto.UserRequestDto;
import com.cnusw.everytown.user.dto.UserResponseDto;
import com.cnusw.everytown.user.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    @ResponseBody
    public ResponseEntity<UserResponseDto> signup(@RequestBody UserRequestDto requestDto) {
        return ResponseEntity.ok(authService.signup(requestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody UserRequestDto requestDto) {
        return ResponseEntity.ok(authService.login(requestDto));
    }


}
