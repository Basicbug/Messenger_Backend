package com.basicbug.messenger.api_server.controller.talk;

import com.basicbug.messenger.api_server.dto.talk.response.TalkRoomBasicResponseDto;
import com.basicbug.messenger.api_server.dto.talk.response.TalkRoomDetailResponseDto;
import com.basicbug.messenger.api_server.model.response.ListResponse;
import com.basicbug.messenger.api_server.model.response.SingleResponse;
import com.basicbug.messenger.api_server.service.ResponseService;
import com.basicbug.messenger.api_server.service.talk.TalkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/talk")
public class TalkController {

    private final ResponseService responseService;
    private final TalkService talkService;

    @ApiOperation(value = "채팅방 생성", notes = "명시된 사용자들이 추가된 채팅방 생성")
    @PostMapping("/create/room")
    public SingleResponse<TalkRoomBasicResponseDto> createTalkRoom(
        @ApiParam(value = "채팅방 이름", required = false, defaultValue = "기본 채팅방 이름") @RequestParam String name,
        @ApiParam(value = "사용자 uid 리스트", required = true) @RequestParam List<String> uids) {

        return responseService.getSingleResponse(talkService.createAndEnterTalkRoom(name, uids));
    }

    @ApiOperation(value = "참여하고 있는 채팅방 목록", notes = "현재 참여하고 있는 채팅방의 목록 리스트 반환")
    @PostMapping("/room/list")
    public ListResponse<TalkRoomBasicResponseDto> getTalkRoomList(
        @ApiParam(value = "사용자 uid", required = true) @RequestParam String uid) {

        return responseService.getListResponse(talkService.getTalkRoomList(uid));
    }

    @ApiOperation(value = "채팅방 상세 정보", notes = "명시된 채팅방에 대한 정보를 반환")
    @GetMapping("/room/detail")
    public SingleResponse<TalkRoomDetailResponseDto> getTalkRoomDetail(
        @ApiParam(value = "채팅방 id", required = true) @RequestParam String roomId
    ) {
        return responseService.getSingleResponse(talkService.getTalkRoomDetail(roomId));
    }
}
