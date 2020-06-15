package com.basicbug.messenger.auth_server.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

/**
 * @author JaewonChoi
 */

@Slf4j
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 전달받은 매개변수는 사용자가 입력한 로그인 정보를 담고 있음. 이를 DB 에 저장된 값과 비교하면 된다. (UserDetailsService 를 주로 이용)
        // TODO 인증과정 필요 + 사용자 정보 + 권한 을 조합하여 토큰생성

        log.info("authentication process begin");
        log.error("TEST");

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(authentication.getName(), null,
            AuthorityUtils.createAuthorityList("USER"));
        return token;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
//        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
