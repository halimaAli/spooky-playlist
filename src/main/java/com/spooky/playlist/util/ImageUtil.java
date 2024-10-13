package com.spooky.playlist.util;

import java.io.InputStream;
import java.net.URL;
import java.util.Base64;

public class ImageUtil {

    public static String getBase64Image(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            InputStream is = url.openStream();
            byte[] bytes = org.apache.commons.io.IOUtils.toByteArray(is);
            return Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
