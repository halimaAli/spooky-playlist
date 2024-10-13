package com.spooky.playlist.service;

import com.spooky.playlist.model.Artist;
import com.spooky.playlist.model.Items;
import com.spooky.playlist.model.Track;
import com.spooky.playlist.model.User;
import com.spooky.playlist.util.ApiPath;
import com.spooky.playlist.util.HttpUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final RestTemplate restTemplate;

    public User getUser(String accessToken) {
        HttpEntity<String> request = HttpUtil.createEmptyRequestEntity(accessToken);
        ResponseEntity<User> response = restTemplate.exchange(ApiPath.USER_URL, HttpMethod.GET, request, User.class);

        return response.getBody();
    }

    /**
     * Retrieves a list of the user's top tracks from Spotify.
     *
     * @param accessToken The OAuth access token to authenticate the request.
     * @return A list of track IDs representing the user's top tracks.
     */
    public List<String> getTopTracks(String accessToken) {
        HttpEntity<String> request = HttpUtil.createEmptyRequestEntity(accessToken);

        //Use ParameterizedTypeReference to provide the generic type information for Items<PlaylistTrack>
        ResponseEntity<Items<Track>> response = restTemplate.exchange(ApiPath.USER_URL + "top/tracks", HttpMethod.GET, request,
                new ParameterizedTypeReference<>() {
                });

        List<String> tracks = new ArrayList<>();
        for (Track track : Objects.requireNonNull(response.getBody()).getItems()) {
            tracks.add(track.getId());
        }

        return tracks;
    }

    /**
     * Retrieves a list of the user's top artists from Spotify.
     *
     * @param accessToken The OAuth access token to authenticate the request.
     * @return A list of artist objects representing the user's top artists.
     */
    public List<Artist> getTopArtists(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity<String> request = new HttpEntity<>("parameters", headers);
        ResponseEntity<Items<Artist>> response = restTemplate.exchange(ApiPath.USER_URL + "top/artists", HttpMethod.GET, request,
                new ParameterizedTypeReference<>() {
                });

        return Objects.requireNonNull(response.getBody()).getItems();
    }

    /**
     * Retrieves a list of the user's top genres from the top artists.
     *
     * @param topArtists The list of the user's top artists.
     * @return A list of strings representing the user's top genres.
     */
    public List<String> getTopGenres(List<Artist> topArtists) {
        Set<String> genres = new HashSet<>();

        for (Artist artist : topArtists) {
            genres.addAll(artist.getGenres());
        }

        return new ArrayList<>(genres);
    }
}
