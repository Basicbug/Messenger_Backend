package com.basicbug.messenger.api_server.service.friend;

import com.basicbug.messenger.api_server.dto.friends.FriendsResponseDto;
import com.basicbug.messenger.api_server.model.friends.Friends;
import com.basicbug.messenger.api_server.model.user.User;
import com.basicbug.messenger.api_server.repository.friend.FriendsRepository;
import com.basicbug.messenger.api_server.service.user.UserService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author JaewonChoi
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class FriendsService {

    private Logger logger = LoggerFactory.getLogger(FriendsService.class);

    private final FriendsRepository friendsRepository;
    private final UserService userService;

    /**
     * getAllFriends uid 의 전체 친구 리스트를 반환한다.
     *
     * @param uid 사용자의 uid
     * @return uid 의 전체 친구 리스트
     */
    public List<FriendsResponseDto> getAllFriends(String uid) {
        Optional<List<Friends>> friendsList = friendsRepository.findAllByUid(uid);

        if (friendsList.isPresent()) {
            List<String> uids = friendsList.get().stream().map(Friends::getUid).collect(Collectors.toList());
            List<FriendsResponseDto> friendsResponseDtos = new ArrayList<>();
            for (User user : userService.findUsersByUid(uids)) {
                friendsResponseDtos.add(new FriendsResponseDto(user.getUid(), user.getName(), user.getStatus()));
            }

            return friendsResponseDtos;
        } else {
            logger.error("getAllFriends return null uid=", uid);
            return null;
        }
    }

    /**
     * addFriends uid 의 친구 리스트에 friendUid 를 추가한다.
     */
    public void addFriends(String uid, String friendUid) {
        friendsRepository.save(Friends.builder()
            .uid(uid)
            .frienduid(friendUid)
            .build());
    }

    /**
     * isFriends 두 uid 가 서로 친구 관계인지 여부를 반환한다.
     *
     * @param uid 사용자의 uid
     * @param friendUid 친구의 uid
     * @return 두 uid 가 친구 관계이면 true, 아니면 false
     */
    public boolean isFriends(String uid, String friendUid) {
        Optional<Friends> friends = friendsRepository.findByUidAndFrienduid(uid, friendUid);
        return friends.isPresent();
    }

}
