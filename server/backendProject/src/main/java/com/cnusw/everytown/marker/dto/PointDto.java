package com.cnusw.everytown.marker.dto;

import lombok.Getter;

@Getter
public class PointDto {
    private int x;
    private int y;

    public PointDto(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
