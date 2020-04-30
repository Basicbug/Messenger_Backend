package com.basicbug.messenger.controller;

import com.basicbug.messenger.service.NaverService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author JaewonChoi
 */

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/social/login")
public class SocialController {

    private Logger logger = LoggerFactory.getLogger(SocialController.class);

    private final Environment env;
    private final RestTemplate restTemplate;
    private final Gson gson;
    private final NaverService naverService;

    @Value("${spring.url.base}")
    private String baseUrl;

    @Value("${spring.social.naver.client_id}")
    private String naverClientId;

    @Value("${spring.social.naver.redirect}")
    private String naverRedirect;

    @GetMapping
    public ModelAndView socialLogin(ModelAndView modelAndView) {
        StringBuilder loginUrl = new StringBuilder();

        loginUrl.append(env.getProperty("spring.social.naver.url.login"))
            .append("?client_id=").append(naverClientId)
            .append("&response_type=code")
            .append("&redirect_uri=").append(baseUrl).append(naverRedirect);

        modelAndView.addObject("loginUrl", loginUrl.toString());
        modelAndView.setViewName("social/login");
        return modelAndView;
    }

    @GetMapping(value = "/naver")
    public ModelAndView redirectNaver(ModelAndView modelAndView, @RequestParam String code) {
        return null;
    }
}
