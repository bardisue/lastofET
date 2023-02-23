package com.cnusw.everytown.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordRequestDto {
    private int id;
    private String exPassword;
    private String newPassword;
}
