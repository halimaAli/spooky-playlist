package com.spooky.playlist.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Playlist {

    private String id;
    private String href;
    private ArrayList<PlaylistTrack> items;
    private Track tracks;
}
