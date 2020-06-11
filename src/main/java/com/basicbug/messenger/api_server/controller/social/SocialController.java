package com.basicbug.messenger.api_server.controller.social;

import com.basicbug.messenger.api_server.dto.social.JwtTokenResponse;
import com.basicbug.messenger.api_server.exception.UserExistException;
import com.basicbug.messenger.api_server.exception.UserNotFoundException;
import com.basicbug.messenger.api_server.model.response.CommonResponse;
import com.basicbug.messenger.api_server.model.response.SingleResponse;
import com.basicbug.messenger.api_server.model.social.NaverAuth;
import com.basicbug.messenger.api_server.model.social.NaverProfile;
import com.basicbug.messenger.api_server.model.user.User;
import com.basicbug.messenger.api_server.repository.user.UserRepository;
import com.basicbug.messenger.api_server.service.social.NaverService;
import com.basicbug.messenger.api_server.service.ResponseService;
import com.basicbug.messenger.auth_server.config.security.JwtTokenProvider;
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
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/social")
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
    @PostMapping(value = "/signin")
    public SingleResponse<JwtTokenResponse> loginByProvider(
        @ApiParam(value = "서비스 제공자") @RequestParam String provider,
        @ApiParam(value = "access token", required = true) @RequestParam String accessToken) {

        String uid = "";
        String name = "";

        if (provider.isEmpty()) {
            // 일반 인증
        } else if (provider.toLowerCase().equals("naver")) {
            // Naver social login 이후
            NaverProfile profile = naverService.getNaverProfile(accessToken);
            if (profile == null) {
                // TODO naver access token 이 유효하지 않음.
                return null;
            }

            uid = profile.getResponse().getId();
            name = profile.getResponse().getName();
        }

        Optional<User> user = userRepository.findByUidAndProvider(uid, provider);

        if (!user.isPresent()) {
            userRepository.save(User.builder()
                .uid(uid)
                .provider(provider)
                .name(name)
                .roles(Collections.singletonList("ROLE_USER"))
                .build());

            user = userRepository.findByUidAndProvider(uid, provider);
        }

        return responseService.getSingleResponse(
            new JwtTokenResponse(jwtTokenProvider.createToken(String.valueOf(user.get().getUid()), user.get().getRoles())));
    }

    @GetMapping(value = "/login/naver")
    public ModelAndView redirectNaver(ModelAndView modelAndView, @RequestParam String code) {
        NaverAuth auth = naverService.getNaverTokenInfo(code);
        modelAndView.addObject("authInfo", auth);
        modelAndView.setViewName("social/redirect");
        return modelAndView;
    }
}
