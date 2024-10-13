package com.spooky.playlist.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Artist {
    List<String> genres;
    String name;
    String id;
    String uri;
    String href;
}
