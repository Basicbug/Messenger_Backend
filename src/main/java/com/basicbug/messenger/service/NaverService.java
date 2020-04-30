package com.basicbug.messenger.service;

import com.basicbug.messenger.model.social.NaverAuth;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * @author JaewonChoi
 */

@Slf4j
@RequiredArgsConstructor
@Service
public class NaverService {

    private Logger logger = LoggerFactory.getLogger(NaverService.class);

    private final RestTemplate restTemplate;
    private final Environment env;
    private final Gson gson;

    @Value("${spring.url.base}")
    private String baseUrl;

    @Value("${spring.social.naver.client_id}")
    private String naverClientId;

    @Value("${spring.social.naver.client_secret}")
    private String naverClientSecret;

    @Value("{spring.social.naver.redirect}")
    private String naverRedirect;

    public NaverAuth getNaverTokenInfo(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", naverClientId);
        params.add("client_secret", naverClientSecret);
        params.add("redirect_uri", baseUrl + naverRedirect);
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(env.getProperty("spring.social.naver.url.token"), request, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return gson.fromJson(response.getBody(), NaverAuth.class);
        }

        logger.error("getNaverTokenInfo request Fail");
        return null;
    }
}
