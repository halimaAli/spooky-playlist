package com.spooky.playlist.service;


import com.spooky.playlist.model.Artist;
import com.spooky.playlist.model.PlaylistTrack;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SpotifyService {
    private final RestTemplate restTemplate;
    private static final String user_URL = "https://api.spotify.com/v1/me/";
    private static final String playlist_URL = "https://api.spotify.com/v1/users/"; // Hali/playlist
    private List<Artist> artists;
    private List<PlaylistTrack> tracks;

    private Map<PlaylistTrack, Map<String, Artist>> halloweenData;


}
