package com.basicbug.messenger.controller;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author JaewonChoi
 */

@Slf4j
@RestController
public class TestController {

    @GetMapping("/test/string")
    public String testString() {
        log.debug("/test/string called");
        return "test string";
    }

    @GetMapping(value = "/test/json")
    public UserTest testJson() {
        UserTest user = new UserTest();
        user.name = "username";
        user.email = "username@email.com";
        user.age = 99;

        return user;
    }

    @Getter
    @Setter
    public static class UserTest {
        private String name;
        private String email;
        private int age;
    }
}
