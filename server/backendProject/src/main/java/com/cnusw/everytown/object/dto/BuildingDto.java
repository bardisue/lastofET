package com.cnusw.everytown.object.dto;

import com.cnusw.everytown.object.entity.Building;
import com.cnusw.everytown.object.enums.ObjectType;
import lombok.Getter;

@Getter
public class BuildingDto extends ObjectDto{

    private String url;
    private String contents;
    private String name;

    public BuildingDto(PointDto point, ObjectType objectType, String objectId, String url, String contents, String name) {
        super(point, objectType, objectId);
        this.url = url;
        this.contents = contents;
        this.name = name;
    }

    public static BuildingDto toDto(Building building) {
        return new BuildingDto(new PointDto(building.getX(), building.getY()), building.getObjectType(),
                building.getObjectId(), building.getUrl(), building.getContents(), building.getName());
    }
}
