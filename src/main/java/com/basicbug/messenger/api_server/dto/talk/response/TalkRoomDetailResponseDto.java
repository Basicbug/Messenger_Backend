package com.basicbug.messenger.api_server.dto.talk.response;

import com.basicbug.messenger.api_server.model.message.TalkMessage;
import com.basicbug.messenger.api_server.model.message.TalkRoom;
import com.basicbug.messenger.api_server.model.user.User;
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
public class TalkRoomDetailResponseDto {

    private String roomId;

    private String name;

    private List<User> participants;

    private TalkMessage lastMessage;

    public TalkRoomDetailResponseDto(TalkRoom talkRoom) {
        this.roomId = talkRoom.getRoomId();
        this.name = talkRoom.getName();
        this.participants = talkRoom.getParticipants();
        this.lastMessage = talkRoom.getLastMessage();
    }
}