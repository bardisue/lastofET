package com.cnusw.everytown.object.entity;


import com.cnusw.everytown.object.enums.ObjectType;
import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@NoArgsConstructor
public abstract class Object {
    @Id
    private String objectId;
    @Enumerated(value = EnumType.STRING)
    @Column
    private ObjectType objectType;

    @Column
    private int x;

    @Column
    private int y;


    public Object(String objectId, ObjectType objectType, int x, int y) {
        this.objectId = objectId;
        this.objectType = objectType;
        this.x = x;
        this.y = y;
    }

}

