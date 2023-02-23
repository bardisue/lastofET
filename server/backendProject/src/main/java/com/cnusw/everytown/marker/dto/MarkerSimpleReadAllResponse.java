package com.cnusw.everytown.marker.dto;


import lombok.Getter;
import lombok.RequiredArgsConstructor;


public record MarkerSimpleReadAllResponse(int marker_id, int user_id, PointDto point, String type) {
}
