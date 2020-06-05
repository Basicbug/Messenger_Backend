package com.basicbug.messenger.controller.test;

import com.basicbug.messenger.config.security.JwtTokenProvider;
import com.basicbug.messenger.model.response.SingleResponse;
import com.basicbug.messenger.service.ResponseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.Arrays;
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

    @ApiOperation(value = "테스트용 jwt 토큰 생성", notes = "임시로 사용할 jwt 토큰 생성")
    @PostMapping("/create/token")
    public SingleResponse<String> createJwtTokenForTestUser(
        @ApiParam(value = "사용자 uid", required = true) @RequestParam String uid
    ) {
        log.info("createJwtTokenForTestUser " + uid);
        return responseService.getSingleResponse(jwtTokenProvider.createToken(uid, Arrays.asList("ROLE_USER")));
    }
}
