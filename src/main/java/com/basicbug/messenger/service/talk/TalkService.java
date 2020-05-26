package com.basicbug.messenger.service.talk;

import com.basicbug.messenger.model.message.TalkMessage;
import com.basicbug.messenger.repository.talk.TalkRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

/**
 * @author JaewonChoi
 */

@Slf4j
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

    /**
     * channelTopic 에 메세지를 전달한다.
     * @param talkMessage 전달하고자 하는 메세지
     */
    public void sendTalkMessage(TalkMessage talkMessage) {
        log.debug("TalkService " + "sendTalkMessage " + talkMessage);
        redisTemplate.convertAndSend(channelTopic.getTopic(), talkMessage);
    }

}
