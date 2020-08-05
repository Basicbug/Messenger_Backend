package com.basicbug.messenger.api_server.dto.friends;

import com.basicbug.messenger.api_server.model.user.User;
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

    public FriendsResponseDto(User user) {
        this.uid = user.getUid();
        this.name = user.getName();
        this.status = user.getStatus();
    }
}
