package com.taomish.app.android.farmsanta.farmer.utils;

import static android.util.Base64.URL_SAFE;
import static android.util.Base64.decode;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class JwtUtil {

    public boolean isTokenValid(String token) {
        String[] parts = token.split("\\."); // split out the "parts" (header, payload and signature)
        if (parts.length < 2) {
            return false;
        }

        try {
            JSONObject bodyJsonObject = new JSONObject(getJson(parts[1]));
            long expiryTime = bodyJsonObject.getLong("exp");
            expiryTime *= 1000;
            long currentSystemTime = System.currentTimeMillis();
            return currentSystemTime < expiryTime;
        } catch (UnsupportedEncodingException | JSONException e) {
            e.printStackTrace();
        }

        return false;
    }

    public String getUserId(String token) {
        String[] parts = token.split("\\."); // split out the "parts" (header, payload and signature)
        if (parts.length < 2) {
            return null;
        }

        try {
            JSONObject bodyJsonObject;
            bodyJsonObject = new JSONObject(getJson(parts[1]));
            return bodyJsonObject.getString("sub");
        } catch (UnsupportedEncodingException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getJson(String strEncoded) throws UnsupportedEncodingException {
        byte[] decodedBytes = decode(strEncoded, URL_SAFE);
        return new String(decodedBytes, StandardCharsets.UTF_8);
    }
}
