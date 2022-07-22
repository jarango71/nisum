package com.nisum.evaluation.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nisum.evaluation.domain.User;
import com.nisum.evaluation.exception.UserNotActivatedException;
import com.nisum.evaluation.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email)
            .orElseThrow(() ->
                new  UsernameNotFoundException("User not found: " + email)
            );

        return createSpringSecurityUser(email, user);
    }
    
    private org.springframework.security.core.userdetails.User createSpringSecurityUser(String email, User user) {
        if (!user.isActive()) {
            throw new UserNotActivatedException("User " + email + " was not activated");
        }
        
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

        return new org.springframework.security.core.userdetails.User(email,
            user.getPassword(),
            grantedAuthorities);
    }

}