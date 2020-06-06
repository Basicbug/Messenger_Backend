package com.basicbug.messenger.service.talk;

import com.basicbug.messenger.model.message.TalkRoom;
import com.basicbug.messenger.model.user.User;
import com.basicbug.messenger.repository.talk.TalkRoomRepository;
import com.basicbug.messenger.service.user.UserService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

/**
 * @author JaewonChoi
 */

@Service
@RequiredArgsConstructor
public class TalkRoomService {

    private Map<String, ChannelTopic> topics;

    private final UserService userService;

    private final TalkRoomRepository talkRoomRepository;

    @PostConstruct
    public void init() {
        topics = new HashMap<>();
    }

    public ChannelTopic getTopic(String roomId) {
        return topics.get(roomId);
    }

    public ChannelTopic createOrGetTopic(String roomId) {
        topics.computeIfAbsent(roomId, s -> {
            return topics.put(roomId, ChannelTopic.of(roomId));
        });
        return topics.get(roomId);
    }

    public TalkRoom createTalkRoomWithParticipants(String roomName, List<User> participants) {
        TalkRoom talkRoom = TalkRoom.builder()
            .roomId(UUID.randomUUID().toString())
            .name(roomName)
            .participants(participants)
            .build();

        return talkRoom;
    }

    public TalkRoom findTalkRoomById(String roomId) {
        return talkRoomRepository.findByRoomId(roomId).orElse(null);
    }
}
