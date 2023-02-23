package com.cnusw.everytown.marker.dto;

import lombok.Getter;

@Getter
public class PhotoMarkerCreatedRequest extends MarkerCreatedRequest {

    private final String title;
    private final String contents;
    private final String url;


    public PhotoMarkerCreatedRequest(int user_id, PointDto point, String title, String contents, String url) {
        super(user_id, point, "Photo");
        this.title = title;
        this.contents = contents;
        this.url = url;
    }


}
