package com.example.demo.controller;

import com.example.demo.api.NotificationApi;
import com.example.demo.facade.PlanFacade;
import com.example.demo.model.Notification;
import com.example.demo.service.NotificationService;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Log4j2
public class NotificationController implements NotificationApi {

  private NotificationService notificationService;
  private PlanFacade planFacade;
  @Override
  public ResponseEntity<List<Notification>> getAllByUser(Principal principal) throws IOException{
    List<Notification> notifications = notificationService.getAllByUser(principal)
        .stream()
        .map(planFacade::apply)
        .collect(Collectors.toList());
    return new ResponseEntity<>(notifications, HttpStatus.OK);
  };

  @Override
  public ResponseEntity<List<Notification>> read(Long id, Principal principal) throws IOException{
    List<Notification> notifications = notificationService.read(id, principal)
        .stream()
        .map(planFacade::apply)
        .collect(Collectors.toList());

    return new ResponseEntity<>(notifications, HttpStatus.OK);
  };
}
