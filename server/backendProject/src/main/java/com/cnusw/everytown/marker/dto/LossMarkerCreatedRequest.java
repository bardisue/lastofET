package com.cnusw.everytown.marker.dto;

import lombok.Getter;

@Getter
public class LossMarkerCreatedRequest extends MarkerCreatedRequest {

    private final String contents;



    public LossMarkerCreatedRequest(int id, PointDto point, String contents) {
        super(id, point, "Loss");
        this.contents = contents;
    }


}
