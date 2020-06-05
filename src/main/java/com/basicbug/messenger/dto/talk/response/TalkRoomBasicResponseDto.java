package com.basicbug.messenger.dto.talk.response;

import com.basicbug.messenger.model.message.TalkRoom;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author JaewonChoi
 */

@Getter
@Setter
@Builder
public class TalkRoomBasicResponseDto {

    private String roomId;

    private String name;

    public TalkRoomBasicResponseDto(TalkRoom talkRoom) {
        this.roomId = talkRoom.getRoomId();
        this.name = talkRoom.getName();
    }
}
