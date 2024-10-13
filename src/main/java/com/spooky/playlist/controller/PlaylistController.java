package com.spooky.playlist.controller;


import com.spooky.playlist.service.GeneratePlaylistService;
import com.spooky.playlist.service.PlaylistService;
import com.spooky.playlist.service.SpotifyService;
import com.spooky.playlist.model.Playlist;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class PlaylistController {

    private final PlaylistService playlistService;
    private final GeneratePlaylistService generatePlaylistService;

    @GetMapping("/generate-playlist")
    public String generatePlaylist(final HttpSession session) {
        String accessToken = (String) session.getAttribute("accessToken");
        String id = (String) session.getAttribute("userId");

        return generatePlaylistService.generatePlaylistItems(accessToken, id);
    }

    @GetMapping("/your-playlist")
    public Playlist displayGeneratedPlaylist(final HttpSession session, final Model model) {
        String accessToken = (String) session.getAttribute("accessToken");
        String playlistId = (String) session.getAttribute("playlistId");
        Playlist playlist = playlistService.getPlaylist(accessToken, playlistId);
        model.addAttribute("playlist", playlistService.getPlaylist(accessToken, playlistId));
        return playlist;
    }
}
