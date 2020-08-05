package com.basicbug.messenger.api_server.service.friend;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.basicbug.messenger.api_server.dto.friends.FriendsResponseDto;
import com.basicbug.messenger.api_server.model.friends.Friends;
import com.basicbug.messenger.api_server.model.user.User;
import com.basicbug.messenger.api_server.repository.friend.FriendsRepository;
import com.basicbug.messenger.api_server.service.user.UserService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

@ExtendWith(MockitoExtension.class)
public class FriendsServiceTest {

    @Mock
    private FriendsRepository friendsRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private FriendsService friendsService;

    @Test
    public void should_ReturnNull_WhenNoFriends() {
        when(friendsRepository.findAllByUid(any())).thenReturn(Optional.empty());

        List<FriendsResponseDto> friendsResponseDtoList = friendsService.getAllFriends("uid");

        assertThat(friendsResponseDtoList).isNullOrEmpty();
    }

    @Test
    public void should_ReturnList_WhenFriendsExists() {
        //Given
        ArrayList<Friends> friendsList = new ArrayList<>(
            Arrays.asList(
                new Friends(1L, "UID", "FRIEND_UID", LocalDateTime.now()),
                new Friends(2L, "UID", "FRIEND2_UID", LocalDateTime.now())
            )
        );
        when(friendsRepository.findAllByUid("UID")).thenReturn(Optional.of(friendsList));
        when(userService.findUsersByUid(Arrays.asList("FRIEND_UID", "FRIEND2_UID")))
            .thenReturn(Arrays.asList(new User(), new User()));

        //When
        List<FriendsResponseDto> friendsResponseDtoList = friendsService.getAllFriends("UID");

        //Then
        assertThat(friendsResponseDtoList).hasSize(friendsList.size());
    }

    @Test
    public void should_ReturnFriends_WhenAdded() {
        ArrayList<Friends> friendsList = new ArrayList<>();

        when(friendsRepository.save(any(Friends.class))).thenAnswer(new Answer<Friends>() {
            @Override
            public Friends answer(InvocationOnMock invocation) throws Throwable {
                Friends friends = invocation.getArgument(0);
                friendsList.add(friends);
                return null;
            }
        });

        friendsService.addFriends("UID", "FRIEND_UID");

        assertThat(friendsList).hasSize(1);
    }

    @Test
    public void should_ReturnTrue_IfFriendsRelationship() {
        when(friendsRepository.findByUidAndFrienduid("UID", "FRIEND_UID"))
            .thenReturn(
                Optional.of(new Friends(1L, "UID", "FRIEND_UID", LocalDateTime.now()))
            );

        boolean isFriends = friendsService.isFriends("UID", "FRIEND_UID");

        assertThat(isFriends).isTrue();
    }
}