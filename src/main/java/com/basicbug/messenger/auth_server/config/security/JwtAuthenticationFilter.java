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
        //TODO websocket 에 대한 요청인 경우 이 작업을 수행할 수 없기 때문에 생략해야 함..?
        log.error("filter with jwt~");

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            log.error("Authentication is not set");

            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse resp = (HttpServletResponse) response;


            String token = req.getHeader(HttpHeaders.AUTHORIZATION);
            if (!token.isEmpty()) {
                log.error("token is attached " + token);
                String[] parts = token.split(" ");
                if (parts.length == 2) {
                    String scheme = parts[0];
                    String credentials = parts[1];

                    String answer = BEARER.matcher(scheme).matches() ? credentials : null;

                    if (answer == null) {
                        log.error("invalid authentication format");
                    } else {
                        //valid check
                        String jwtToken = jwtTokenProvider.resolveToken(answer);
                        if (jwtTokenProvider.validateToken(jwtToken)) {
                            log.error("authentication valid");
                        } else {
                            log.error("valid authentication but fail");
                        }
                    }
                }
            }
        } else {
            log.error("Already authenticated");
        }


        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
        if (token != null && jwtTokenProvider.validateToken(token)) {
            log.info("JwtTokenProvider success");
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            log.info("JwtTokenProvider fail");
        }
        chain.doFilter(request, response);
    }


}
