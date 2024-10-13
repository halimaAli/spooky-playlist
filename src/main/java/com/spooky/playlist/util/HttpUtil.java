package com.spooky.playlist.util;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;


public class HttpUtil {

    private static HttpHeaders createAuthHeader(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        return headers;
    }

    private static HttpHeaders createAuthHeaderWithContentType(String accessToken, MediaType contentType) {
        HttpHeaders headers = createAuthHeader(accessToken);
        headers.setContentType(contentType);
        return headers;
    }

    public static <T> HttpEntity<T> createRequestEntity(T body, String accessToken, MediaType mediaType) {
        HttpHeaders headers = createAuthHeaderWithContentType(accessToken, mediaType);
        return new HttpEntity<>(body, headers);
    }

    public static HttpEntity<String> createEmptyRequestEntity(String accessToken) {
        return new HttpEntity<>("parameters", createAuthHeader(accessToken));
    }
}
