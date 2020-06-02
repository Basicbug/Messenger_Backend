package com.basicbug.messenger.model.message;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author JaewonChoi
 */

@Getter
@Setter
public class TalkMessage {

    private String roomId;
    private String senderUid;
    private String message;
    private LocalDateTime timestamp;
}
