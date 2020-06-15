package com.basicbug.messenger.auth_server.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * @author JaewonChoi
 */

@Profile("dev")
@Configuration
@RequiredArgsConstructor
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private final CustomAuthenticationProvider authenticationProvider;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
            .authenticationProvider(authenticationProvider)
            .authorizeRequests()
            .antMatchers("/v1/social/**", "/v1/test/**").permitAll()
            .antMatchers("/v1/**").authenticated();
    }

}
