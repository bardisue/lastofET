package com.cnusw.everytown.marker.converter;

import com.cnusw.everytown.marker.dto.MarkerSimpleReadAllResponse;
import com.cnusw.everytown.marker.dto.MarkerSimpleResponse;
import com.cnusw.everytown.marker.dto.PointDto;
import com.cnusw.everytown.marker.entity.Marker;

public class Converter {

    public static MarkerSimpleResponse toMarkerSimpleResponse(Marker marker) {
        return new MarkerSimpleResponse(marker.getId(), marker.getUser().getId(), new PointDto(marker.getX(), marker.getY()));
    }

    public static MarkerSimpleReadAllResponse toMarkerSimpleReadAllResponse(Marker marker) {
        return new MarkerSimpleReadAllResponse(marker.getId(), marker.getUser().getId(), new PointDto(marker.getX(), marker.getY()), marker.getMarker_type());
    }

}
