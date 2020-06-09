package com.basicbug.messenger.api_server.model.social;

import lombok.Getter;
import lombok.Setter;

/**
 * @author JaewonChoi
 */

@Getter
@Setter
public class NaverAuth {

    private String access_token;
    private String refresh_token;
    private String token_type;
    private long expires_in;
}
