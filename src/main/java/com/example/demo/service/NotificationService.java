package com.example.demo.service;

import com.example.demo.entity.NotificationItem;
import com.example.demo.entity.UserItem;
import com.example.demo.repository.NotificationRepository;
import com.example.demo.repository.UserRepository;
import java.security.Principal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class NotificationService {

  private final NotificationRepository notificationRepository;
  private final UserRepository userRepository;

  public List<NotificationItem> getAllByUser(Principal principal){
    UserItem user = getUserByPrincipal(principal);
    return notificationRepository.findAllByUserOrderByDate(
        user);
  }

  public List<NotificationItem> read(Long id, Principal principal){
    notificationRepository.findById(id)
        .map(n -> {
          n.setIsRead(true);
          return n;
        }).ifPresent(notificationRepository::save);

    return getAllByUser(principal);
  }

  private UserItem getUserByPrincipal(Principal principal) {
    String username = principal.getName();
    return userRepository.findUserItemByUsername(username)
        .orElseThrow(
            () -> new UsernameNotFoundException("Username not found with username " + username));
  }
}
