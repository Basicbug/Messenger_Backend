package com.basicbug.messenger.controller.user;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.basicbug.messenger.model.response.ListResponse;
import com.basicbug.messenger.model.response.SingleResponse;
import com.basicbug.messenger.model.user.User;
import com.basicbug.messenger.repository.user.UserRepository;
import com.basicbug.messenger.service.ResponseService;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

/**
 * @author JaewonChoi
 */

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = UserController.class)
@WithMockUser(value = "test-user", roles = "USER")
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private ResponseService responseService;

    @Test
    @DisplayName("회원등록_정상케이스")
    public void registerUser_should_success() throws Exception {
        User user = User.builder()
            .uid("test uid")
            .name("test name")
            .status("test status message")
            .build();

        SingleResponse<User> expectedResponse = new SingleResponse<>();
        expectedResponse.setData(user);

        given(userRepository.save(any())).willReturn(user);
        given(responseService.getSingleResponse(user)).willReturn(expectedResponse);

        mockMvc.perform(post("/v1/user")
            .with(csrf())
            .param("uid", user.getUid())
            .param("name", user.getName())
            .param("status", user.getStatus()))
            .andExpect(status().isOk())
            .andExpect(content().json(new Gson().toJson(expectedResponse)))
            .andDo(print());
    }

    @Test
    @DisplayName("회원등록_필수파라미터_추가되지않은_예외케이스")
    public void registerUser_should_fail() throws Exception {
        //필수 항목인 name 이 추가되지 않은 경우
        User user = User.builder()
            .uid("test uid")
            .status("test status message")
            .build();

        SingleResponse<User> expectedResponse = new SingleResponse<>();
        expectedResponse.setData(user);

        given(responseService.getSingleResponse(user)).willReturn(expectedResponse);

        mockMvc.perform(post("/v1/user")
            .with(csrf())
            .param("uid", user.getUid())
            .param("status", user.getStatus()))
            .andExpect(status().is4xxClientError())
            .andDo(print());
    }

    @Test
    @DisplayName("전체_회원조회_테스트")
    public void findAllUsers() throws Exception {
        List<User> users = new ArrayList<>();

        users.add(User.builder()
            .uid("1")
            .name("A")
            .status("A")
            .build());
        users.add(User.builder()
            .uid("2")
            .name("B")
            .status("B")
            .build());
        users.add(User.builder()
            .uid("3")
            .name("C")
            .status("C")
            .build());

        ListResponse<User> expectedResponse = new ListResponse<>();
        expectedResponse.setDataList(users);

        given(userRepository.findAll()).willReturn(users);
        given(responseService.getListResponse(users)).willReturn(expectedResponse);

        mockMvc.perform(get("/v1/users"))
            .andExpect(status().isOk())
            .andExpect(content().json(new Gson().toJson(expectedResponse)))
            .andDo(print());
    }
}