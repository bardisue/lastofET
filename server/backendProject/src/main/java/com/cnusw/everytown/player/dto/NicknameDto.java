package com.cnusw.everytown.player.dto;

import lombok.Getter;

@Getter
public class NicknameDto {
    private String nickname;

    public NicknameDto(String nickname) {
        this.nickname = nickname;
    }
}
