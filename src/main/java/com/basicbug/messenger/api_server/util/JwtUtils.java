package com.basicbug.messenger.api_server.util;

import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;

/**
 * @author JaewonChoi
 */
public class JwtUtils {

    private static final Pattern BEARER = Pattern.compile("^Bearer$", Pattern.CASE_INSENSITIVE);

    public static String extractJwtToken(HttpServletRequest request) {
        String auth = request.getHeader(HttpHeaders.AUTHORIZATION);

        String[] parts = auth.split(" ");
        if (parts.length == 2) {
            String scheme = parts[0];
            String credentials = parts[1];
            return BEARER.matcher(scheme).matches() ? credentials : null;
        }

        return null;
    }
}
