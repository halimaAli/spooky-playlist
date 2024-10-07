package com.spooky.playlist.service;

import com.spooky.playlist.config.SpotifyConfigurationProperties;
import com.spooky.playlist.util.AccessTokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@EnableConfigurationProperties(value = SpotifyConfigurationProperties.class)
public class AccessTokenService {

    private final AuthService authService;
    private final RestTemplate restTemplate;
    private final SpotifyConfigurationProperties properties;
    private static final String URL = "https://accounts.spotify.com/api/token";

    public String getToken(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_id", properties.getClientId());
        map.add("grant_type", "authorization_code");
        map.add("code", code);
        map.add("redirect_uri", properties.getRedirectUrl());
        map.add("code_verifier", authService.getCodeVerifier());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<AccessTokenDto> response = restTemplate.postForEntity(URL, request, AccessTokenDto.class);
        return response.getBody().getAccess_token();
    }
}
