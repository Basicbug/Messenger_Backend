package com.basicbug.messenger.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

/**
 * @author JaewonChoi
 */

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    @Value("spring.jwt.secret")
    private String secretKey;

    private Long tokenValidTimeInMillis = 1000L * 60 * 60;

    private final UserDetailsService userDetailsService;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    /**
     * 주어진 정보를 이용하여 JWT 토큰 생성
     * @param userPk 사용자 primary key
     * @param roles  사용자에게 부여할 role
     * @return 생성된 JWT 토큰
     */
    public String createToken(String userPk, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(userPk);
        claims.put("roles", roles);
        Date now = new Date();
        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(new Date(now.getTime() + tokenValidTimeInMillis))
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact();
    }

    /**
     * JWT 토큰으로 인증 정보 조회
     * @param token
     * @return 인증 정보
     */
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(getUserPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    /**
     * JWT 토큰에서 사용자의 primary key 추출
     * @param token
     * @return 추출된 primary key
     */
    public String getUserPk(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    /**
     * Request Header 에서 Token 값 파싱
     * @param request
     * @return 파싱된 token 값
     */
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("X-AUTH-TOKEN");
    }

    /**
     * Authorization bearer 값에서 token 값 파싱.
     * @param bearerToken
     * @return 파싱된 token 값
     */
    public String resolveToken(String bearerToken) {
        return bearerToken.replace("Bearer", "").trim();
    }

    /**
     * JWT token 유효성 및 만료기간 확인
     * @param token
     * @return 유효하다면 true, 아니면 false
     */
    public boolean validateToken(String token) {
        //TODO parsing error handling (invalid format, expire ...)
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
        } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException e) {
            log.error("Invalid Jwt format", e.getMessage());
            return false;
        } catch (SignatureException e) {
            log.error("JWS Signature validation fail", e.getMessage());
            return false;
        } catch (ExpiredJwtException e) {
            log.error("JWT is expired", e.getMessage());
            return false;
        }

        return true;
    }
}
