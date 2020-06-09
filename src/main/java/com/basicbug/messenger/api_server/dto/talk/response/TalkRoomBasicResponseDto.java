package com.basicbug.messenger.api_server.dto.talk.response;

import com.basicbug.messenger.api_server.model.message.TalkRoom;
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
public class TalkRoomBasicResponseDto {

    private String roomId;

    private String name;

    public TalkRoomBasicResponseDto(TalkRoom talkRoom) {
        this.roomId = talkRoom.getRoomId();
        this.name = talkRoom.getName();
    }
}
