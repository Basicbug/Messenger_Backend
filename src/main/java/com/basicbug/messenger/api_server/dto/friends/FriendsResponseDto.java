package com.basicbug.messenger.api_server.dto.friends;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author JaewonChoi
 */

@Getter
@AllArgsConstructor
public class FriendsResponseDto {

    private String uid;

    private String name;

    private String status;
}
