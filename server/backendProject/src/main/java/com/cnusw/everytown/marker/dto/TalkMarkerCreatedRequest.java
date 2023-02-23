package com.cnusw.everytown.marker.dto;

import lombok.Getter;

@Getter
public class TalkMarkerCreatedRequest extends MarkerCreatedRequest {

    private final String title;
    private final String contents;

    public TalkMarkerCreatedRequest(int user_id, PointDto point, String title, String contents) {
        super(user_id, point,"Talk");
        this.title = title;
        this.contents = contents;
    }


}
