package com.basicbug.messenger.api_server.controller.friends;

import com.basicbug.messenger.api_server.dto.friends.FriendsResponseDto;
import com.basicbug.messenger.api_server.model.friends.Friends;
import com.basicbug.messenger.api_server.model.response.CommonResponse;
import com.basicbug.messenger.api_server.model.response.ListResponse;
import com.basicbug.messenger.api_server.model.response.SingleResponse;
import com.basicbug.messenger.api_server.model.user.User;
import com.basicbug.messenger.api_server.service.friend.FriendsService;
import com.basicbug.messenger.api_server.service.ResponseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bytecode.Throw;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author JaewonChoi
 */

@Api(tags = {"Friends"})
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/friends")
public class FriendsController {

    private Logger logger = LoggerFactory.getLogger(FriendsController.class);

    private final FriendsService friendsService;
    private final ResponseService responseService;

    @ApiOperation(value = "친구 목록", notes = "사용자의 친구 리스트를 반환")
    @GetMapping("/list")
    public CommonResponse findAllFriends() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            logger.error("/v1/friends/{uid} has no authentication");
            throw new Exception("authentication is null");
        }

        User user = (User) authentication.getPrincipal();
        return responseService.getListResponse(friendsService.getAllFriends(user.getUid()));
    }

    @ApiOperation(value = "친구 추가", notes = "사용자의 친구 목록에 친구를 추가한다.")
    @PostMapping("/add")
    public CommonResponse addFriends(
        @ApiParam(value = "사용자 uid", required = true) @RequestParam String uid,
        @ApiParam(value = "친구 uid", required = true) @RequestParam String friendUid) {

        /*
        TODO
        addFriends fail case needs to be added (when friends already exists)
         */
        friendsService.addFriends(uid, friendUid);
        return responseService.getSuccessResponse();
    }

    @ApiOperation(value = "친구 관계 여부 확인", notes = "두 사용자가 서로 친구 관계인지 여부를 확인한다.")
    @PostMapping(value = "/check")
    public SingleResponse<Boolean> isFriends(
        @ApiParam(value = "사용자 uid", required = true) @RequestParam String uid,
        @ApiParam(value = "친구 uid", required = true) @RequestParam String friendUid) {

        return responseService.getSingleResponse(friendsService.isFriends(uid, friendUid));
    }
}
