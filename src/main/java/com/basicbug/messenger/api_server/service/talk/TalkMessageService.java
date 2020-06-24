package com.basicbug.messenger.api_server.service.talk;

import com.basicbug.messenger.api_server.model.message.TalkMessage;
import com.basicbug.messenger.api_server.repository.talk.TalkMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author JaewonChoi
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class TalkMessageService {

    private final TalkMessageRepository talkMessageRepository;

    public TalkMessage getTalkMessageById(String messageId, String roomId) {
        TalkMessage talkMessage = talkMessageRepository.findTalkMessageByIdAndRoomId(messageId, roomId);
        if (talkMessage == null) {
            log.info("getTalkMessageById is null", messageId, roomId);
        }

        return talkMessage;
    }
}
