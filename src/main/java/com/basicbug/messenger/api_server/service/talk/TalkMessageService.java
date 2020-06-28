package com.basicbug.messenger.api_server.service.talk;

import com.basicbug.messenger.api_server.model.message.TalkMessage;
import com.basicbug.messenger.api_server.repository.talk.TalkMessageRepository;
import com.basicbug.messenger.chatting_server.dto.TalkMessageRequestDto;
import java.util.UUID;
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

    public TalkMessage getTalkMessageById(String mid, String roomId) {
        TalkMessage talkMessage = talkMessageRepository.findTalkMessageByIdAndRoomId(mid, roomId);
        if (talkMessage == null) {
            log.info("getTalkMessageById is null", mid, roomId);
        }

        return talkMessage;
    }

    public void saveTalkMessage(TalkMessageRequestDto talkMessageRequestDto) {
        TalkMessage talkMessage = TalkMessage.builder()
            .mid("msg-" + UUID.randomUUID())
            .roomId(talkMessageRequestDto.getRoomId())
            .senderUid(talkMessageRequestDto.getSenderUid())
            .timestamp(talkMessageRequestDto.getTimestamp())
            .message(talkMessageRequestDto.getMessage())
            .build();

        talkMessageRepository.save(talkMessage);
    }
}
