package com.spooky.playlist.service;

import com.spooky.playlist.code.CodeChallenge;
import com.spooky.playlist.code.CodeVerifier;
import com.spooky.playlist.config.SpotifyConfigurationProperties;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

@Data
@Service
@RequiredArgsConstructor
@EnableConfigurationProperties(SpotifyConfigurationProperties.class)
public class AuthService {

    private final SpotifyConfigurationProperties properties;
    private String codeVerifier;

    public String getAuthURL() {
        final String codeVerifier = CodeVerifier.generate();
        setCodeVerifier(codeVerifier);

        String authURL = "https://accounts.spotify.com/authorize";
        String codeChallenge = CodeChallenge.generate(codeVerifier);

        return authURL + "?client_id=" + properties.getClientId()
                + "&response_type=code&redirect_uri=" + properties.getRedirectUrl()
                +"&code_challenge_method=S256&code_challenge=" + codeChallenge;
    }
}
