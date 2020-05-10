package com.basicbug.messenger.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author JaewonChoi
 */

@Api(tags = {"0. Test"})
@Slf4j
@RestController
@RequestMapping(value = "/v1/test")
public class TestController {

    @ApiOperation(value = "문자열 값 조회", notes = "String 값 반환 테스트")
    @GetMapping("/string")
    public String testString() {
        log.debug("/test/string called");
        return "test string";
    }

    @ApiOperation(value = "객체 값 조회", notes = "User 객체 반환 테스트")
    @GetMapping(value = "/json")
    public UserTest testJson() {
        UserTest user = new UserTest("username", "username@email.com", 99);
        return user;
    }

    @ApiOperation(value = "객체 저장", notes = "User 객체 저장 테스트")
    @PostMapping(value = "/save")
    public UserTest testSave(@ApiParam(value = "이름", required = true) @RequestParam String name,
        @ApiParam(value = "이메일", required = true) @RequestParam String email,
        @ApiParam(value = "나이", required = false) @RequestParam int age) {
        UserTest user = UserTest.builder()
            .name(name)
            .email(email)
            .age(age)
            .build();

        return user;
    }

    @Builder
    @Data
    public static class UserTest {

        private String name;
        private String email;
        private int age;
    }
}
