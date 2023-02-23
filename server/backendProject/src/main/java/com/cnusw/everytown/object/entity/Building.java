package com.cnusw.everytown.object.entity;

import com.cnusw.everytown.object.dto.BuildingDto;
import com.cnusw.everytown.object.enums.ObjectType;
import com.cnusw.everytown.object.entity.Object;
import javax.persistence.Column;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Building extends Object {
    @Column
    private String url;
    @Column
    private String contents;
    @Column
    private String name;

    public Building (String objectId, ObjectType objectType, int x, int y, String url, String contents, String name) {
        super(objectId, objectType, x, y);
        this.url = url;
        this.contents = contents;
        this.name = name;
    }


    public static Building toEntity(BuildingDto dto) {
        return new Building(dto.getObjectId(), dto.getObjectType(), dto.getPoint().getX(), dto.getPoint().getY(),
                dto.getUrl(), dto.getContents(), dto.getName());
    }

}
