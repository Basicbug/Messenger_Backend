package com.basicbug.messenger.chatting_server.dto;

import com.basicbug.messenger.api_server.model.message.TalkMessage;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author JaewonChoi
 */

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TalkMessageResponseDto implements Serializable {

    private String mid;

    private String roomId;

    private String senderUid;

    private String message;

    private LocalDateTime timestamp;

    public TalkMessageResponseDto(TalkMessage talkMessage) {
        this.mid = talkMessage.getMid();
        this.roomId = talkMessage.getRoomId();
        this.senderUid = talkMessage.getSenderUid();
        this.message = talkMessage.getMessage();
        this.timestamp = talkMessage.getTimestamp();
    }
}
