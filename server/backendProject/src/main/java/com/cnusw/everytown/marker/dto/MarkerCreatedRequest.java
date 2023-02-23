package com.cnusw.everytown.marker.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MarkerCreatedRequest {

    protected final int user_id;

    protected final PointDto point;
    protected final String type;

}