package com.cnusw.everytown.marker.entity;

import javax.persistence.*;

import com.cnusw.everytown.user.entity.User;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;


@Entity
@SuperBuilder
@Inheritance(strategy = InheritanceType.JOINED)
//@DiscriminatorColumn(name="dtype")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public abstract class Marker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;

    @ManyToOne
    @JoinColumn(name="user_id")
    protected User user;

    @Column
    protected int x;

    @Column
    protected int y;

    @Column
    protected LocalDateTime created_datetime;

    @Column
    protected String marker_type;

    public Marker(User user, int x, int y, LocalDateTime created_datetime, String marker_type) {
        this.user = user;
        this.x = x;
        this.y = y;
        this.created_datetime = created_datetime;
        this.marker_type = marker_type;
    }


}


