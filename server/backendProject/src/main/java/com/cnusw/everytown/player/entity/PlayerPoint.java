package com.cnusw.everytown.player.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.cnusw.everytown.player.dto.PlayerPointDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PlayerPoint {

    @Id
    private String nickname;

    @Column
    private int x;

    @Column
    private int y;

    public static PlayerPoint toEntity(PlayerPointDto dto) {
        return new PlayerPoint(dto.getNickname(), dto.getPoint().getX(), dto.getPoint().getY());
    }

    public PlayerPoint(String nickname, int x, int y) {
        this.nickname = nickname;
        this.x = x;
        this.y = y;
    }
}
