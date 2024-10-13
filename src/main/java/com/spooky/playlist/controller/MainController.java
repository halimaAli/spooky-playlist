package com.spooky.playlist.controller;


import com.spooky.playlist.model.Artist;
import com.spooky.playlist.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class MainController {

    private final UserService userService;

    @GetMapping("/top-artists")
    public List<Artist> topArtists(final HttpSession session) {
        String accessToken = (String) session.getAttribute("accessToken");
        return userService.getTopArtists(accessToken);
    }

    @GetMapping("/top-tracks")
    public List<String> topTracks(final HttpSession session) {
        String accessToken = (String) session.getAttribute("accessToken");
        return userService.getTopTracks(accessToken);
    }

    @GetMapping("/top-genres")
    public List<String> topGenres(final HttpSession session) {
        String accessToken = (String) session.getAttribute("accessToken");
        List<Artist> topArtists = userService.getTopArtists(accessToken);
        return userService.getTopGenres(topArtists);
    }


}
