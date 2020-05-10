package com.basicbug.messenger.controller.message;

import com.basicbug.messenger.model.message.TalkRoom;
import com.basicbug.messenger.model.response.SingleResponse;
import com.basicbug.messenger.repository.TalkRoomRepository;
import com.basicbug.messenger.service.ResponseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final TalkRoomRepository talkRoomRepository;

    @ApiOperation(value = "채팅방 생성", notes = "명시된 사용자들이 추가된 채팅방 생성")
    @PostMapping("/create/room")
    public SingleResponse<TalkRoom> createTalkRoom(
        @ApiParam(value = "채팅방 이름", required = false, defaultValue = "기본 채팅방 이름") @RequestParam String name) {
        TalkRoom talkRoom = talkRoomRepository.createTalkRoom(name);
        return responseService.getSingleResponse(talkRoom);
    }
}
