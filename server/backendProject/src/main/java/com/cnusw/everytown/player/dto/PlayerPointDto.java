package com.cnusw.everytown.player.dto;

import com.cnusw.everytown.player.entity.PlayerPoint;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PlayerPointDto {
        private PointDto point;
        private String nickname;

        public static PlayerPointDto toDto(PlayerPoint userPoint){
                return new PlayerPointDto(new PointDto(userPoint.getX(), userPoint.getY()), userPoint.getNickname());
        }
}

