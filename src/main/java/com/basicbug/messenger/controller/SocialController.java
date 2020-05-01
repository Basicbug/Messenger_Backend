package com.basicbug.messenger.controller;

import com.basicbug.messenger.config.security.JwtTokenProvider;
import com.basicbug.messenger.exception.UserExistException;
import com.basicbug.messenger.exception.UserNotFoundException;
import com.basicbug.messenger.model.response.CommonResponse;
import com.basicbug.messenger.model.response.SingleResponse;
import com.basicbug.messenger.model.social.NaverAuth;
import com.basicbug.messenger.model.social.NaverProfile;
import com.basicbug.messenger.model.user.User;
import com.basicbug.messenger.repository.UserRepository;
import com.basicbug.messenger.service.NaverService;
import com.basicbug.messenger.service.ResponseService;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.Collections;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author JaewonChoi
 */

@Api(tags = {"Social"})
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/social")
public class SocialController {

    private Logger logger = LoggerFactory.getLogger(SocialController.class);

    private final Environment env;
    private final RestTemplate restTemplate;
    private final Gson gson;
    private final NaverService naverService;
    private final ResponseService responseService;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Value("${spring.url.base}")
    private String baseUrl;

    @Value("${spring.social.naver.client_id}")
    private String naverClientId;

    @Value("${spring.social.naver.redirect}")
    private String naverRedirect;

    @GetMapping(value = "/login")
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

    @ApiOperation(value = "소셜 로그인", notes = "엑세스 토큰을 이용한 로그인")
    @PostMapping(value = "/signin/{provider}")
    public SingleResponse<String> loginByProvider(
        @ApiParam(value = "서비스 제공자", defaultValue = "naver") @PathVariable String provider,
        @ApiParam(value = "access token", required = true) @RequestParam String accessToken) {

        NaverProfile profile = naverService.getNaverProfile(accessToken);
        User user = userRepository.findByUidAndProvider(profile.getResponse().getId(), provider).orElseThrow(
            UserNotFoundException::new);

        return responseService.getSingleResponse(jwtTokenProvider.createToken(String.valueOf(user.getId()), user.getRoles()));
    }

    @ApiOperation(value = "소셜 가입", notes = "소셜 계정을 이용한 회원가입")
    @PostMapping(value = "/signup/{provider}")
    public CommonResponse signupByProvider(
        @ApiParam(value = "서비스 제공자", required = true, defaultValue = "naver") @PathVariable String provider,
        @ApiParam(value = "access token", required = true) @RequestParam String accessToken) {

        NaverProfile profile = naverService.getNaverProfile(accessToken);
        Optional<User> user = userRepository.findByUidAndProvider(profile.getResponse().getId(), provider);
        if (user.isPresent()) {
            throw new UserExistException();
        }

        userRepository.save(User.builder()
            .uid(profile.getResponse().getId())
            .provider(provider)
            .name(profile.getResponse().getName())
            .roles(Collections.singletonList("ROLE_USER")).build());

        return responseService.getSuccessResponse();
    }

    @GetMapping(value = "/login/naver")
    public ModelAndView redirectNaver(ModelAndView modelAndView, @RequestParam String code) {
        NaverAuth auth = naverService.getNaverTokenInfo(code);
        modelAndView.addObject("authInfo", auth);
        modelAndView.setViewName("social/redirect");
        return modelAndView;
    }
}
