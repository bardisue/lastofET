package com.cnusw.everytown.marker.dto;


import lombok.Getter;
import lombok.RequiredArgsConstructor;


public record MarkerSimpleResponse(int marker_id, int user_id, PointDto point) {
}
