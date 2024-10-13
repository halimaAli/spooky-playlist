package com.spooky.playlist.service;


import com.spooky.playlist.model.Artist;
import com.spooky.playlist.model.Track;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class GeneratePlaylistService {
    private final PlaylistService playlistService;
    private final UserService userService;
    private final HalloweenService halloweenService;

    /**
     * DEPRECATED
     * Creates Halloween Playlist using the Spotify API of recommendations.
     * Problem: Result is not good as it return a playlist with not Halloween themed songs.
     * <p>
     * public List<String> getHalloweenRecommendations(String accessToken, List<String> seedTrackIds) {
     * <p>
     * HttpHeaders headers = new HttpHeaders();
     * headers.set("Authorization", "Bearer " + accessToken);
     * <p>
     * <p>
     * String seedTracks = seedTrackIds.get(0) + "," + seedTrackIds.get(1);
     * String seedGenres = "goth,industrial,black-metal";
     * <p>
     * String url = "https://api.spotify.com/v1/recommendations"
     * + "?seed_tracks=" + seedTracks
     * + "&seed_genres=" + seedGenres
     * + "&target_valence=0.2"  // Low happiness level for dark mood
     * + "&target_energy=0.4"  // Moderate energy for eerie vibe
     * + "&target_mode=0"      // Minor key (dark, mysterious)
     * + "&target_instrumentalness=0.5"
     * + "&limit=50"// Higher chance of instrumental tracks
     * + "&target_tempo=100";  // Keep tempo controlled and atmospheric
     * <p>
     * HttpEntity<String> request = new HttpEntity<>(headers);
     * ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.GET, request, Object.class);
     * <p>
     * LinkedHashMap result = (LinkedHashMap) response.getBody();
     * List<LinkedHashMap> tracks = (List<LinkedHashMap>) result.get("tracks");
     * <p>
     * List<String> trackUris = new ArrayList<>();
     * for (LinkedHashMap track : tracks) {
     * trackUris.add((String) track.get("uri"));
     * }
     * <p>
     * return trackUris;
     * }
     */

    public String generatePlaylistItems(String accessToken, String userID) {
        List<String> topTracks = userService.getTopTracks(accessToken);
        List<Artist> topArtists = userService.getTopArtists(accessToken);
        List<String> topGenres = userService.getTopGenres(topArtists);


        Map<Set<Artist>, Track> artistsToTracks = halloweenService.getHalloweenTracksData(accessToken);
        Map<Set<String>, Artist> artistsToGenres = halloweenService.getHalloweenGenresData(accessToken);

        Set<String> playlistTracksUris = new HashSet<>();

        for (String topTrack : topTracks) {
            for (Track halloweenTrack : artistsToTracks.values()) {
                if (halloweenTrack.getId().equals(topTrack)) {
                    playlistTracksUris.add(halloweenTrack.getUri());
                }
            }
        }

        for (Artist artist : topArtists) {
            for (Set<Artist> halloweenArtist : artistsToTracks.keySet()) {
                if (halloweenArtist.stream().anyMatch(ha -> ha.getId().equals(artist.getId()))) {
                    playlistTracksUris.add(artistsToTracks.get(halloweenArtist).getUri());
                }
            }
        }


        Set<Artist> relevantArtists = new HashSet<>();
        for (String topGenre : topGenres) {
            String normalizedTopGenre = normalizeGenre(topGenre);
            for (Set<String> genres : artistsToGenres.keySet()) {
                Set<String> normalizedGenres = new HashSet<>();
                for (String genre : genres) {
                    normalizedGenres.add(normalizeGenre(genre));
                }
                if (normalizedGenres.contains(normalizedTopGenre)) {
                    relevantArtists.add(artistsToGenres.get(genres));
                }
            }
        }

        for (Artist artist : relevantArtists) {
            if (topArtists.stream().anyMatch(a -> a.getId().equals(artist.getId()))) {
                playlistTracksUris.add(artistsToTracks.get(artist).getUri());
            }
        }

        if (playlistTracksUris.size() < 50) {
            List<Track> halloweenTracksList = new ArrayList<>(artistsToTracks.values());
            Random random = new Random();

            while (playlistTracksUris.size() < 50) {
                Track randomHalloweenTrack = halloweenTracksList.get(random.nextInt(halloweenTracksList.size()));
                playlistTracksUris.add(randomHalloweenTrack.getUri());
            }
        }

        String playlist_Id = playlistService.createPlaylist(accessToken, userID);

        return playlistService.addSongs(accessToken, playlist_Id, new ArrayList<>(playlistTracksUris));
    }

    private String normalizeGenre(String genre) {
        genre = genre.toLowerCase();
        genre = genre.replace("&", "and");
        genre = genre.replaceAll("[^a-zA-Z ]", "");  //removes non-alphabetic characters

        // Additional mappings for common genre aliases
        Map<String, String> genreMappings = new HashMap<>();
        genreMappings.put("r&b", "rhythm and blues");
        genreMappings.put("rb", "rhythm and blues");

        return genreMappings.getOrDefault(genre, genre);
    }
}
