package com.cnusw.everytown.marker.entity;


import com.cnusw.everytown.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@SuperBuilder
@AllArgsConstructor
//@DiscriminatorValue("Loss")
@NoArgsConstructor
@Getter
public class LossMarker extends Marker{

    @Column
    private String contents;

    public LossMarker(User user, int x, int y, LocalDateTime created_datetime, String contents) {
        super(user, x, y, created_datetime, "Loss");
        this.contents = contents;
    }
}
