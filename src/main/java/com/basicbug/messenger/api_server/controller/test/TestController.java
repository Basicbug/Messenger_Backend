package com.basicbug.messenger.api_server.controller.test;

import com.basicbug.messenger.api_server.dto.social.JwtTokenResponse;
import com.basicbug.messenger.api_server.model.response.SingleResponse;
import com.basicbug.messenger.api_server.model.user.User;
import com.basicbug.messenger.api_server.repository.user.UserRepository;
import com.basicbug.messenger.api_server.service.ResponseService;
import com.basicbug.messenger.auth_server.config.security.JwtTokenProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author JaewonChoi
 */

@Api(tags = {"Test"})
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/test")
public class TestController {

    private final ResponseService responseService;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @ApiOperation(value = "테스트용 jwt 토큰 생성", notes = "임시로 사용할 jwt 토큰 생성")
    @PostMapping("/create/token")
    public SingleResponse<JwtTokenResponse> createJwtTokenForTestUser(
        @ApiParam(value = "사용자 uid", required = true) @RequestParam String uid
    ) {
        log.info("createJwtTokenForTestUser " + uid);

        String name = "TEST-" + UUID.randomUUID();

        User user = userRepository.findByUidAndProvider(uid, "").orElse(null);
        if (user == null) {
            userRepository.save(User.builder()
                .uid(uid)
                .provider("")
                .name(name)
                .roles(Collections.singletonList("ROLE_USER"))
                .build());

            user = userRepository.findByUidAndProvider(uid, "").orElse(null);
        }

        return responseService
            .getSingleResponse(new JwtTokenResponse(jwtTokenProvider.createToken(String.valueOf(user.getUid()), user.getRoles())));
    }
}
