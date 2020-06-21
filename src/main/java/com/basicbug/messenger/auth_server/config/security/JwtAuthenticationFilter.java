package com.basicbug.messenger.auth_server.config.security;

import com.basicbug.messenger.api_server.util.JwtUtils;
import java.io.IOException;
import java.util.regex.Pattern;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
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

            String jwtToken = JwtUtils.extractJwtToken(req);

            if (jwtToken != null) {
                try {
                    String token = jwtTokenProvider.resolveToken(jwtToken);
                    Authentication authentication = jwtTokenProvider.getAuthentication(token);

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

}
