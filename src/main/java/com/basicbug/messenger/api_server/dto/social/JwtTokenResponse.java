package com.basicbug.messenger.api_server.dto.social;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author JaewonChoi
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
public class JwtTokenResponse {
    private String jwtToken;
}
