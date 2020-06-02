package com.basicbug.messenger.model.message;

import com.basicbug.messenger.model.user.User;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
    private List<User> participants;
    private TalkMessage lastMessage;

    public static TalkRoom create(String name) {
        TalkRoom talkRoom = new TalkRoom();
        talkRoom.roomId = UUID.randomUUID().toString();
        talkRoom.name = name;
        talkRoom.participants = new ArrayList<>();
        return talkRoom;
    }

    public void participate(User user) {
        participants.add(user);
    }

    public void leave(User user) {
        participants.remove(user);
    }
}
