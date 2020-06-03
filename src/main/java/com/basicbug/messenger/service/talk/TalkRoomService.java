package com.basicbug.messenger.service.talk;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

/**
 * @author JaewonChoi
 */

@RequiredArgsConstructor
@Service
public class TalkRoomService {

    private Map<String, ChannelTopic> topics;

    public ChannelTopic getTopic(String roomId) {
        return topics.get(roomId);
    }
}
