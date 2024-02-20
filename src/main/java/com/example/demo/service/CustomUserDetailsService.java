package com.example.demo.service;

import com.example.demo.entity.UserItem;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserItem userModel = userRepository.findUserItemByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found with username: " + username));

        return build(userModel);
    }

    public UserItem loadUserById(Long userid) {
        return userRepository.findUserItemByUserId(userid).orElse(null);
    }


    public static UserItem build(UserItem user) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toList());

        return new UserItem(
                user.getUserId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                authorities);
    }
}

