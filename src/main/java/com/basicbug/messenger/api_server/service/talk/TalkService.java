package com.basicbug.messenger.api_server.service.talk;

import com.basicbug.messenger.api_server.dto.talk.response.TalkRoomBasicResponseDto;
import com.basicbug.messenger.api_server.dto.talk.response.TalkRoomDetailResponseDto;
import com.basicbug.messenger.api_server.model.message.TalkRoom;
import com.basicbug.messenger.api_server.model.user.User;
import com.basicbug.messenger.api_server.service.user.UserService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

/**
 * @author JaewonChoi
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class TalkService {

    private final TalkRoomService talkRoomService;
    private final UserService userService;

    /**
     * destination 의 URL 의 제일 마지막에 위치한 roomId 추출.
     *
     * @return roomId
     */
    public String getRoomId(String destination) {
        int lastIndex = destination.lastIndexOf("/");
        if (lastIndex != -1) {
            return destination.substring(lastIndex + 1);
        } else {
            return "";
        }
    }

    public ChannelTopic getTopic(String roomId) {
        return talkRoomService.getTopic(roomId);
    }

    /**
     * roomName 을 가진 talkRoom 을 생성 후, 명시된 uid 를 가진 사용자들을 해당 talkRoom 에 참여하도록 한다.
     *
     * @param roomName 채팅방 이름
     * @param uids 참여할 사용자 uid 리스트
     * @return 생성된 채팅방
     */
    public TalkRoomBasicResponseDto createAndEnterTalkRoom(String roomName, List<String> uids) {
        List<User> participants = userService.findUsersByUid(uids);

        TalkRoom talkRoom = talkRoomService.createTalkRoomWithParticipants(roomName, participants);

        return TalkRoomBasicResponseDto.builder()
            .name(talkRoom.getName())
            .roomId(talkRoom.getRoomId())
            .build();
    }

    /**
     * uid 를 가진 사용자가 현재 참여하고 있는 채팅방 목록을 반환한다.
     *
     * @param uid 사용자 uid
     * @return 사용자가 참여하고 있는 채팅방 목록
     */
    public List<TalkRoomBasicResponseDto> getTalkRoomList(String uid) {
        User user = userService.findUserByUid(uid);

        List<TalkRoom> talkRooms = user.getParticipating_rooms();
        List<TalkRoomBasicResponseDto> basicResponseDtos = new ArrayList<>();
        for (TalkRoom talkRoom : talkRooms) {
            basicResponseDtos.add(new TalkRoomBasicResponseDto(talkRoom));
        }

        return basicResponseDtos;
    }

    public TalkRoomDetailResponseDto getTalkRoomDetail(String roomId) {
        TalkRoom talkRoom = talkRoomService.findTalkRoomById(roomId);
        return new TalkRoomDetailResponseDto(talkRoom);
    }

    public boolean participateToRoom(String roomId, String uid) {
        TalkRoom talkRoom = talkRoomService.findTalkRoomById(roomId);

        if (talkRoom == null) {
            log.info("participateToRoom roomId is invalid", roomId);
            return false;
        }

        User user = userService.findUserByUid(uid);

        if (user == null) {
            log.info("participateToRoom uid is invalid", uid);
            return false;
        }

        //FIXME ManyToMany 에서 이렇게 양방향으로 데이터를 추가해 주어야 할까?
        talkRoom.participate(user);
        user.participateToRoom(talkRoom);

        return true;
    }
}
