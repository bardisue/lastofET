package com.cnusw.everytown.object.dto;

import com.cnusw.everytown.object.enums.ObjectType;
import com.cnusw.everytown.object.entity.Object;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ObjectDto{
    private PointDto point;
    private ObjectType objectType;
    private String objectId;

    public static ObjectDto toDto(Object object){
        return new ObjectDto(new PointDto(object.getX(), object.getY()), object.getObjectType(), object.getObjectId());
    }
}
