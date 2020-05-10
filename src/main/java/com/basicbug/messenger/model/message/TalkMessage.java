package com.basicbug.messenger.model.message;

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
}
