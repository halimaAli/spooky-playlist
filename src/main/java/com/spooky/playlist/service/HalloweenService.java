package com.spooky.playlist.service;


import com.spooky.playlist.model.*;
import com.spooky.playlist.util.ApiPath;
import com.spooky.playlist.util.HttpUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@RequiredArgsConstructor
public class HalloweenService {

    private final RestTemplate restTemplate;
    private List<PlaylistTrack> tracks;

    private List<Playlist> getPlaylistsByCategory(String accessToken) {
        HttpEntity<String> request = HttpUtil.createEmptyRequestEntity(accessToken);
        ResponseEntity<Category> response = restTemplate.exchange(ApiPath.SPOTIFY_ROOT + "/browse/categories/halloween/playlists?limit=10", HttpMethod.GET, request, Category.class);
        return Objects.requireNonNull(response.getBody()).getPlaylists().getItems();
    }

    private List<PlaylistTrack> getHalloweenTracks(String accessToken) {
        List<Playlist> halloweenPlaylists = getPlaylistsByCategory(accessToken);
        List<PlaylistTrack> tracks = new ArrayList<>();

        Set<String> hrefs = new HashSet<>();
        for (Playlist playlist : halloweenPlaylists) {
            hrefs.add(playlist.getTracks().getHref());
        }

        HttpEntity<String> request = HttpUtil.createEmptyRequestEntity(accessToken);

        for (String href : hrefs) {
            ResponseEntity<Playlist> response = restTemplate.exchange(href, HttpMethod.GET, request, Playlist.class);
            tracks.addAll(Objects.requireNonNull(response.getBody()).getItems());
        }
        return tracks;
    }

    private Map<Set<String>, Artist> getHalloweenGenres(String accessToken, List<PlaylistTrack> tracks) {
        Map<Set<String>, Artist> artistToGenreMap = new HashMap<>();
        Set<String> halloweenArtistsIds = new HashSet<>();

        for (PlaylistTrack track : tracks) {
            for (Artist artist : track.getTrack().getArtists()) {
                halloweenArtistsIds.add(artist.getHref());
            }
        }

        List<Artist> artists = getHalloweenArtists(accessToken, halloweenArtistsIds);

        for (Artist artist : artists) {
            Set<String> halloweenGenres = new HashSet<>(artist.getGenres());
            artistToGenreMap.put(halloweenGenres, artist);
        }

        return artistToGenreMap;
    }

    private List<Artist> getHalloweenArtists(String accessToken, Set<String> halloweenArtistsHrefs) {
        HttpEntity<String> request = HttpUtil.createEmptyRequestEntity(accessToken);
        List<Artist> artists = new ArrayList<>();

        for (String href : halloweenArtistsHrefs) {
            ResponseEntity<Artist> response = restTemplate.exchange(href, HttpMethod.GET, request, Artist.class);
            artists.add(Objects.requireNonNull(response.getBody()));
        }
        return artists;
    }

    public Map<Set<Artist>, Track> getHalloweenTracksData(String accessToken) {
        tracks = getHalloweenTracks(accessToken);
        Map<Set<Artist>, Track> trackToArtistIdsMap = new HashMap<>();

        for (PlaylistTrack track : tracks) {
            Set<Artist> artists = new HashSet<>(track.getTrack().getArtists());
            trackToArtistIdsMap.put(artists, track.getTrack());
        }

        return trackToArtistIdsMap;
    }

    public Map<Set<String>, Artist> getHalloweenGenresData(String accessToken) {
        return getHalloweenGenres(accessToken, tracks);
    }
}
