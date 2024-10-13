package com.spooky.playlist.service;


import com.spooky.playlist.model.Items;
import com.spooky.playlist.model.Playlist;
import com.spooky.playlist.model.PlaylistTrack;
import com.spooky.playlist.util.ApiPath;
import com.spooky.playlist.util.HttpUtil;
import com.spooky.playlist.util.ImageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PlaylistService {

    private final RestTemplate restTemplate;

    public String createPlaylist(String accessToken, String id) {
        Map<String, Object> body = new HashMap<>();
        body.put("name", "Spooky Playlist-Dev");
        body.put("description", "Your Own Halloween Playlist");
        body.put("public", false); // Doesn't add the playlist to the users profile. It's still public accessible.

        HttpEntity<Map<String, Object>> request = HttpUtil.createRequestEntity(body, accessToken, MediaType.APPLICATION_JSON);
        ResponseEntity<Playlist> response = restTemplate.postForEntity(String.format(ApiPath.USER_PLAYLIST_URL, id), request, Playlist.class);

        String playlistId = Objects.requireNonNull(response.getBody()).getId();

        setPlaylistImage(accessToken, playlistId, ApiPath.IMAGE_URL);
        return playlistId;
    }

    public void setPlaylistImage(String accessToken, String playlistId, String imageUrl) {
        String imageBase64 = ImageUtil.getBase64Image(imageUrl);
        HttpEntity<String> request = HttpUtil.createRequestEntity(imageBase64, accessToken, MediaType.IMAGE_JPEG);
        restTemplate.exchange(String.format(ApiPath.PLAYLIST_URL, playlistId) + "/images", HttpMethod.PUT, request, Object.class);
    }

    public String addSongs(String accessToken, String playlistId, List<String> uris) {
        ArrayList<String> body = new ArrayList<>(uris);

        HttpEntity<ArrayList<String>> request = HttpUtil.createRequestEntity(body, accessToken, MediaType.APPLICATION_JSON);
        ResponseEntity<String> response = restTemplate.postForEntity(String.format(ApiPath.PLAYLIST_URL, playlistId) + "/tracks", request, String.class);

        return response.getStatusCode().toString();
    }

    public Items<PlaylistTrack> getPlaylistTracks(String accessToken, String playlistId) {
        HttpEntity<String> request = HttpUtil.createEmptyRequestEntity(accessToken);
        ResponseEntity<Items<PlaylistTrack>> response = restTemplate.exchange(String.format(ApiPath.PLAYLIST_URL, playlistId) + "/tracks", HttpMethod.GET, request, new ParameterizedTypeReference<>() {
        });

        return response.getBody();
    }

    public Playlist getPlaylist(String accessToken, String playlistId) {
        HttpEntity<String> request = HttpUtil.createEmptyRequestEntity(accessToken);
        ResponseEntity<Playlist> response = restTemplate.exchange(String.format(ApiPath.PLAYLIST_URL, playlistId), HttpMethod.GET, request, Playlist.class);

        return response.getBody();
    }
}
