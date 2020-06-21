package com.basicbug.messenger.api_server.controller.user;

import com.basicbug.messenger.api_server.dto.user.UserInfoResponseDto;
import com.basicbug.messenger.api_server.exception.UserNotFoundException;
import com.basicbug.messenger.api_server.model.response.ListResponse;
import com.basicbug.messenger.api_server.model.response.SingleResponse;
import com.basicbug.messenger.api_server.model.user.User;
import com.basicbug.messenger.api_server.repository.user.UserRepository;
import com.basicbug.messenger.api_server.service.ResponseService;
import com.basicbug.messenger.api_server.service.user.UserService;
import com.basicbug.messenger.api_server.util.JwtUtils;
import com.basicbug.messenger.auth_server.config.security.JwtTokenProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author JaewonChoi
 */

@Api(tags = {"User"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class UserController {

    private static final Pattern BEARER = Pattern.compile("^Bearer$", Pattern.CASE_INSENSITIVE);

    private final UserRepository userRepository;
    private final ResponseService responseService;
    private final JwtTokenProvider tokenProvider;

    @ApiOperation(value = "전체 회원 조회", notes = "등록된 전체 회원 조회")
    @GetMapping(value = "/users")
    public ListResponse<User> findAllUser() {
        return responseService.getListResponse(userRepository.findAll());
    }

    @ApiOperation(value = "단일 회원 조회", notes = "id로 회원 조회")
    @GetMapping("/user/{id}")
    public SingleResponse<User> findUserById(@PathVariable long id) {
        return responseService.getSingleResponse(userRepository.findById(id).orElse(null));
    }

    @ApiOperation(value = "단일 회원 조회", notes = "token 으로 회원 조회")
    @GetMapping("/user/me")
    public SingleResponse<UserInfoResponseDto> findUserByToken(HttpServletRequest request, HttpServletResponse response) {
        String jwtToken = JwtUtils.extractJwtToken(request);
        String uid = tokenProvider.getUserPk(jwtToken);
        return responseService.getSingleResponse(new UserInfoResponseDto(userRepository.findByUid(uid).orElse(null)));
    }

    @ApiOperation(value = "회원 등록", notes = "회원 등록")
    @PostMapping(value = "/user")
    public SingleResponse<User> save(
        @ApiParam(value = "회원 아이디", required = true) @RequestParam String uid,
        @ApiParam(value = "회원 이름", required = true) @RequestParam String name,
        @ApiParam(value = "회원 상태 메세지", required = false) @RequestParam String status) {
        User user = User.builder()
            .uid(uid)
            .name(name)
            .status(status)
            .build();

        return responseService.getSingleResponse(userRepository.save(user));
    }
}
