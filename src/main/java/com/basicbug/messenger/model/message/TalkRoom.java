package com.basicbug.messenger.model.message;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;

/**
 * @author JaewonChoi
 */

@Getter
public class TalkRoom {

    private String roomId;
    private Set<String> participants = new HashSet<>();

    @Builder
    public TalkRoom(String roomId, List<String> participants) {
        this.roomId = roomId;
        this.participants = new HashSet<>(participants);
    }

}
