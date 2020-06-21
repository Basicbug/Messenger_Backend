package com.basicbug.messenger.api_server.dto.user;

import com.basicbug.messenger.api_server.model.message.TalkRoom;
import com.basicbug.messenger.api_server.model.user.User;
import java.util.ArrayList;
import java.util.List;
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
public class UserInfoResponseDto {

    private String uid;

    private String name;

    private String provider;

    private String status;

    private List<String> participating_rooms;

    public UserInfoResponseDto(User user) {
        this.uid = user.getUid();
        this.name = user.getName();
        this.provider = user.getProvider();
        this.status = user.getStatus();
        this.participating_rooms = new ArrayList<>();
        for (TalkRoom talkRoom : user.getParticipating_rooms()) {
            participating_rooms.add(talkRoom.getRoomId());
        }
    }
}
