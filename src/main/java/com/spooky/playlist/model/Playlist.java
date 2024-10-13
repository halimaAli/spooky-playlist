package com.spooky.playlist.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Playlist {

    private String id;
    private String href;
    private Items<Track> items;
    private Track tracks;
}
