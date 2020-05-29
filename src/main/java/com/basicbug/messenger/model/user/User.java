package com.basicbug.messenger.model.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author JaewonChoi
 */

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true, length = 30)
    private String uid;

    @Column(nullable = false, length = 100)
    private String name;

    @JsonProperty(access = Access.WRITE_ONLY)
    @Column(nullable = true, length = 100)
    private String password;

    @Column(length = 100)
    private String provider;

    @Column(length = 100)
    private String status;

    //TODO roomID 를 set 으로 관리하는 것이 더 좋은가.? -> 혹시나 중복으로 들어올 수 있는 경우 방지
    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private Set<String> roomIds = new HashSet<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @JsonProperty(access = Access.WRITE_ONLY)
    @Override
    public String getPassword() {
        return null;
    }

    @JsonProperty(access = Access.WRITE_ONLY)
    @Override
    public String getUsername() {
        return this.uid;
    }

    @JsonProperty(access = Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonProperty(access = Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonProperty(access = Access.WRITE_ONLY)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonProperty(access = Access.WRITE_ONLY)
    @Override
    public boolean isEnabled() {
        return false;
    }

    /**
     * 사용자가 roomId 를 가진 채팅방에 참여한다.
     * @param roomId 채팅방 id
     */
    public void participateToRoom(String roomId) {
        roomIds.add(roomId);
    }

    /**
     * 사용자가 roomId 를 가진 채팅방을 나온다.
     * @param roomId 채팅방 id
     */
    public void leaveFromRoom(String roomId) {
        roomIds.remove(roomId);
    }
}
