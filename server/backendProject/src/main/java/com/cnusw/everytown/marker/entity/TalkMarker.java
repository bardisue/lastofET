package com.cnusw.everytown.marker.entity;

import com.cnusw.everytown.user.entity.User;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@SuperBuilder
@AllArgsConstructor
//@DiscriminatorValue("Talk")
@NoArgsConstructor
@ToString
@Getter
public class TalkMarker extends Marker {

    @Column
    private String title;

    @Column
    private String contents;


    public TalkMarker(User user, int x, int y, LocalDateTime created_datetime, String title, String contents) {
        super(user, x, y, created_datetime, "Talk");
        this.title = title;
        this.contents = contents;
    }
}
