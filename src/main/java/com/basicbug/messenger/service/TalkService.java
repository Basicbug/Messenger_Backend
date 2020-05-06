package com.basicbug.messenger.service;

import com.basicbug.messenger.model.message.TalkRoom;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author JaewonChoi
 */

@Slf4j
@RequiredArgsConstructor
@Service
public class TalkService {

    private Map<String, TalkRoom> talkRooms;

    @PostConstruct
    private void init() {
        talkRooms = new HashMap<>();
    }

    public TalkRoom createChatRoom(List<String> participants) {
        String talkRoomId = UUID.randomUUID().toString();
        TalkRoom talkRoom = TalkRoom.builder()
            .roomId(talkRoomId)
            .participants(participants)
            .build();
        talkRooms.put(talkRoomId, talkRoom);

        return talkRoom;
    }
}
