package com.basicbug.messenger.model.message;

import com.basicbug.messenger.model.user.User;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author JaewonChoi
 */

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TalkRoom implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String roomId;
    private String name;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<User> participants = new ArrayList<>();

    @OneToOne
    private TalkMessage lastMessage;

    public static TalkRoom create(String name) {
        TalkRoom talkRoom = new TalkRoom();
        talkRoom.roomId = UUID.randomUUID().toString();
        talkRoom.name = name;
        talkRoom.participants = new ArrayList<>();
        return talkRoom;
    }

    public void participate(User user) {
        participants.add(user);
    }

    public void leave(User user) {
        participants.remove(user);
    }
}
