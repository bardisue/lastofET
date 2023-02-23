package com.cnusw.everytown.marker.entity;

import javax.persistence.*;

import com.cnusw.everytown.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;

@Entity
@SuperBuilder
@AllArgsConstructor
//@DiscriminatorValue("Photo")
@NoArgsConstructor
@Getter
public class PhotoMarker extends Marker{

    @Column
    @Nullable
    private String title;

    @Column
    private String contents;

    @Column
    private String url;

    public PhotoMarker(User user, int x, int y, LocalDateTime created_datetime, @Nullable String title, String contents, String url) {
        super(user, x, y, created_datetime, "Photo");
        this.title = title;
        this.contents = contents;
        this.url = url;
    }
}
