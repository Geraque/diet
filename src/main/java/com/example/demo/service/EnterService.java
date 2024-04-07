package com.example.demo.service;

import com.example.demo.entity.EnterItem;
import com.example.demo.entity.UserItem;
import com.example.demo.repository.EnterRepository;
import com.example.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class EnterService {

  private final EnterRepository enterRepository;

  private final UserRepository userRepository;

  public EnterItem saveEnter(String username) {
    UserItem user = getUserByUsername(username);
    EnterItem enter = new EnterItem();
    enter.setUserId(user.getUserId());

    log.info("Saving enter for userId: {}", user.getUserId());
    return enterRepository.save(enter);
  }

  public EnterItem saveEnterByEmail(String email) {
    UserItem user = getUserByEmail(email);
    EnterItem enter = new EnterItem();
    enter.setUserId(user.getUserId());

    log.info("Saving enter for userId: {}", user.getUserId());
    return enterRepository.save(enter);
  }

  public UserItem getUserByUsername(String username) {
    return userRepository.findUserItemByUsername(username)
        .orElseThrow(
            () -> new UsernameNotFoundException("Username not found with username " + username));
  }

  public UserItem getUserByEmail(String email) {
    return userRepository.findUserItemByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("Username not found with email " + email));
  }
}
