package com.cnusw.everytown.user.controller;

import com.cnusw.everytown.user.dto.ChangePasswordRequestDto;
import com.cnusw.everytown.user.dto.UserResponseDto;
import com.cnusw.everytown.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getMyUserInfo() {
        UserResponseDto myInfoBySecurity = userService.getMyInfoBySecurity();
        System.out.println(myInfoBySecurity.getId());
        return ResponseEntity.ok((myInfoBySecurity));
    }

    @PostMapping("/password")
    public ResponseEntity<UserResponseDto> setUserPassword(@RequestBody ChangePasswordRequestDto requestDto) {
        return ResponseEntity.ok(userService.changeUserPassword(requestDto.getExPassword(), requestDto.getNewPassword()));
    }
}
