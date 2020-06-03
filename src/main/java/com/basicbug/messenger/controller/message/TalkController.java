package com.basicbug.messenger.controller.message;

import com.basicbug.messenger.model.message.TalkRoom;
import com.basicbug.messenger.model.response.ListResponse;
import com.basicbug.messenger.model.response.SingleResponse;
import com.basicbug.messenger.model.user.User;
import com.basicbug.messenger.repository.talk.TalkRoomRepositoryTemp;
import com.basicbug.messenger.repository.user.UserRepository;
import com.basicbug.messenger.service.ResponseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author JaewonChoi
 */

@Api(tags = {"Talk"})
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/talk")
public class TalkController {

    private Logger logger = LoggerFactory.getLogger(TalkController.class);

    private final ResponseService responseService;
    private final TalkRoomRepositoryTemp talkRoomRepositoryTemp;
    private final UserRepository userRepository;

    @ApiOperation(value = "채팅방 생성", notes = "명시된 사용자들이 추가된 채팅방 생성")
    @PostMapping("/create/room")
    public SingleResponse<TalkRoom> createTalkRoom(
        @ApiParam(value = "채팅방 이름", required = false, defaultValue = "기본 채팅방 이름") @RequestParam String name,
        @ApiParam(value = "생성자 uid", required = true) @RequestParam String uid) {
        TalkRoom talkRoom = talkRoomRepositoryTemp.createTalkRoom(name);
        User user = userRepository.findByUid(uid).orElse(null);

        if (user == null) {
            //TODO Invalid User Exception
            return null;
        }

        userRepository.save(user);

        talkRoom.participate(user);

        return responseService.getSingleResponse(talkRoom);
    }

    @ApiOperation(value = "참여하고 있는 채팅방 목록", notes = "현재 참여하고 있는 채팅방의 목록 리스트 반환")
    @PostMapping("/room/list")
    public ListResponse<TalkRoom> getTalkRoomList(
        @ApiParam(value = "사용자 uid", required = true) @RequestParam String uid) {
        User user = userRepository.findByUid(uid).orElse(null);

        if (user == null) {
            //TODO Invalid User Exception;
            return null;
        }

        List<TalkRoom> talkRooms = new ArrayList<>();
//        for (String roomId : user.getRoomIds()) {
//            TalkRoom talkRoom = talkRoomRepository.findTalkRoomById(roomId);
//            talkRooms.add(talkRoom);
//        }

        return responseService.getListResponse(talkRooms);
    }

    @ApiOperation(value = "채팅방 상세 정보", notes = "명시된 채팅방에 대한 정보를 반환")
    @GetMapping("/room/detail")
    public void getTalkRoomDetail() {

    }
}
