package com.basicbug.messenger.service.talk;

import com.basicbug.messenger.model.message.TalkMessage;
import com.basicbug.messenger.repository.talk.TalkRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

/**
 * @author JaewonChoi
 */

@RequiredArgsConstructor
@Service
public class TalkService {

    private final ChannelTopic channelTopic;
    private final RedisTemplate redisTemplate;
    private final TalkRoomRepository talkRoomRepository;

    /**
     * destination 의 URL 의 제일 마지막에 위치한 roomId 추출.
     * @param destination
     * @return roomId
     */
    public String getRoomId(String destination) {
        int lastIndex = destination.lastIndexOf("/");
        if (lastIndex != -1) {
            return destination.substring(lastIndex + 1);
        } else {
            return "";
        }
    }

    public void sendTalkMessage(TalkMessage talkMessage) {

    }

}
