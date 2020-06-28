package com.basicbug.messenger.chatting_server.dto;

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
public class TalkMessageRequestDto implements Serializable {

    private String roomId;

    private String senderUid;

    private String message;

    private LocalDateTime timestamp;
}
