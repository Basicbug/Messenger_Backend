package com.basicbug.messenger.auth_server.config.security;

import java.io.IOException;
import java.util.regex.Pattern;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

/**
 * @author JaewonChoi
 */
@Slf4j
public class JwtAuthenticationFilter extends GenericFilterBean {

    private static final Pattern BEARER = Pattern.compile("^Bearer$", Pattern.CASE_INSENSITIVE);

    private JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {

        if (SecurityContextHolder.getContext().getAuthentication() == null) {

            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse resp = (HttpServletResponse) response;

            String authorizationToken = extractAuthorizationToken(req);

            if (authorizationToken != null) {

                try {
                    String jwtToken = jwtTokenProvider.resolveToken(authorizationToken);
                    Authentication authentication = jwtTokenProvider.getAuthentication(jwtToken);

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } catch (Exception e) {
                    log.error("jwt token verify fail", e.getMessage());
                }
                log.info("Authentication success");
            } else {
                log.error("Authorization header is not valid");
            }
        } else {
            log.info("SecurityContext is already contained");
        }

        chain.doFilter(request, response);
    }

    private String extractAuthorizationToken(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (!token.isEmpty()) {
            String[] parts = token.split(" ");
            if (parts.length == 2) {
                String scheme = parts[0];
                String credentials = parts[1];

                return BEARER.matcher(scheme).matches() ? credentials : null;
            }
        }
        return null;
    }
}
