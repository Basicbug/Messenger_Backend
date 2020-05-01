package com.basicbug.messenger.model.social;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author JaewonChoi
 */

@Getter
@Setter
@ToString
public class NaverProfile {

    private String resultcode;
    private String message;
    private Response response;

    @Getter
    @Setter
    @ToString
    public static class Response {
        private String id;
        private String profile_image;
        private String email;
        private String name;

    }
}
