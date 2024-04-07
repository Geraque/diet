package com.example.demo.service.utils;

import com.example.demo.entity.NotificationItem;
import com.example.demo.entity.UserItem;
import com.example.demo.repository.NotificationRepository;
import java.time.LocalDateTime;
import java.time.ZoneId;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class SendNotification {
  private final NotificationRepository repository;

  public void apply(UserItem user, String heading, String text){
    NotificationItem notification = NotificationItem.builder()
        .user(user)
        .heading(heading)
        .text(text)
        .date(LocalDateTime.now().atZone(ZoneId.of("UTC+4")))
        .isRead(false)
        .build();
    repository.save(notification);
  }

}
