package com.basicbug.messenger.repository;

import com.basicbug.messenger.model.message.TalkRoom;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

/**
 * @author JaewonChoi
 */

@Repository
public class TalkRoomRepository {

    private Map<String, TalkRoom> talkRoomMap;

    @PostConstruct
    private void init() {
        talkRoomMap = new HashMap<>();
    }

    public TalkRoom createTalkRoom(String name) {
        TalkRoom talkRoom = TalkRoom.create(name);
        talkRoomMap.put(talkRoom.getRoomId(), talkRoom);

        return talkRoom;
    }

    public TalkRoom findTalkRoomById(String roomId) {
        return talkRoomMap.get(roomId);
    }

}
