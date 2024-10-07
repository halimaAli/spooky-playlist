package com.spooky.playlist.code;

import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import static org.slf4j.helpers.Reporter.error;

@Slf4j
public class CodeChallenge {

    // transform (hash) it using the SHA256 algorithm. This is the value that will be sent within the user authorization request
    public static String generate(final String codeVerifier) {
        byte[] digest = null;
        try {
            byte[] bytes = codeVerifier.getBytes("US-ASCII");
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(bytes, 0, bytes.length);
            digest = messageDigest.digest();
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException exception) {
            error("Unable to generate code challenge {}", exception);
        }
        return Base64.getUrlEncoder().withoutPadding().encodeToString(digest);
    }
}
