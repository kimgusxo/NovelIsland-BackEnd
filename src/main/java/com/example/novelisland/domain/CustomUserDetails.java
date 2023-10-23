package com.example.novelisland.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {

    private final User user; // 여기서 User는 사용자 엔티티 또는 모델 클래스입니다.

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 사용자의 권한을 리턴하는 메서드
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")); // 단순 예제, 사용자의 권한에 따라 맞게 구현
    }

    @Override
    public String getPassword() {
        return user.getUserPassword(); // 사용자의 비밀번호 리턴
    }

    @Override
    public String getUsername() {
        return user.getUserId(); // 사용자의 이름 또는 식별자 리턴
    }

    // 아래의 메서드들은 사용자 계정의 유효성을 검사하기 위한 추가 정보를 리턴할 수 있습니다.

    @Override
    public boolean isAccountNonExpired() {
        return true; // 계정 만료 여부 리턴
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // 계정 잠금 여부 리턴
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 자격 증명(비밀번호)의 만료 여부 리턴
    }

    @Override
    public boolean isEnabled() {
        return true; // 계정 활성화 여부 리턴
    }
}