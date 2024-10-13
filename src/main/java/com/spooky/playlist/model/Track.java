package com.spooky.playlist.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Track {

    private String name;
    private String id;
    private String href;
    private Album album;
    private String uri;
    private List<Artist> artists;
}
