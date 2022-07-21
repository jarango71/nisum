package com.nisum.evaluation.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.nisum.evaluation.domain.User;

import java.util.Collection;
import java.util.Objects;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class UserPrinciple implements UserDetails {
	private static final long serialVersionUID = -9029343740569635557L;

	private Long id;
	private String name;
	private String email;
    private String password;
    private Collection<? extends GrantedAuthority> authorities = null;

    public static UserPrinciple build(User user) {
        return new UserPrinciple(
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getPassword(),
            null
        );
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserPrinciple user = (UserPrinciple) o;
        return Objects.equals(id, user.id);
    }
}