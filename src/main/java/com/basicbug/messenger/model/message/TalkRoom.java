package com.basicbug.messenger.model.message;

import java.io.Serializable;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author JaewonChoi
 */

@Getter
@Setter
public class TalkRoom implements Serializable {

    private String roomId;
    private String name;

    public static TalkRoom create(String name) {
        TalkRoom talkRoom = new TalkRoom();
        talkRoom.roomId = UUID.randomUUID().toString();
        talkRoom.name = name;

        return talkRoom;
    }
}
