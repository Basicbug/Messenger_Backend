package com.basicbug.messenger.auth_server.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * @author JaewonChoi
 */

@Configuration
@Profile("dev")
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityDevConfiguration extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomAuthenticationProvider customAuthenticationProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authenticationProvider(customAuthenticationProvider)
            .httpBasic().disable()
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .headers()
            .frameOptions().disable()
            .and()
            .authorizeRequests()
            .antMatchers("/stomp/**").hasRole("USER")
//            .antMatchers( "/*/signin", "/*/signup", "/social/**", "/v1/**", "/stomp/**").permitAll()
            .antMatchers("/v1/test/**").authenticated();
//            .anyRequest()
//            .hasRole("USER")
//            .and()
//            .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
            .ignoring()
            .antMatchers("/v2/api-docs", "/swagger-resources/**", "/swagger-ui.html", "/webjars/**", "/swagger/**", "/h2-console/**");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(customAuthenticationProvider);
//            .inMemoryAuthentication()
//            .withUser("qwebnm7788")
//            .password("password")
//            .roles("USER");
    }

}
