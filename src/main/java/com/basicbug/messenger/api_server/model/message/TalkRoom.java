package com.basicbug.messenger.api_server.model.message;

import com.basicbug.messenger.api_server.model.user.User;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
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
public class TalkRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "room_id", nullable = false, unique = true)
    private String roomId;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "participating_rooms")
    private List<User> participants = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "last_message_timestamp")
    private TalkMessage lastMessage;

    public void participate(User user) {
        participants.add(user);
    }

    public void leave(User user) {
        participants.remove(user);
    }
}
