package com.spooky.playlist.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "com.spooky.playlist")
public class SpotifyConfigurationProperties {

    private String clientId;       // Maps to com.spooky.playlist.client-id
    private String redirectUrl;
}
