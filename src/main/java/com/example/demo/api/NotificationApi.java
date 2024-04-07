package com.example.demo.api;

import com.example.demo.model.Notification;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("api/notification")
public interface NotificationApi {

  @CrossOrigin
  @GetMapping("/user")
  public ResponseEntity<List<Notification>> getAllByUser(Principal principal) throws IOException;

  @CrossOrigin
  @PostMapping("/{id}/read")
  public ResponseEntity<List<Notification>> read(@PathVariable("id") Long id, Principal principal) throws IOException;

  @CrossOrigin
  @PostMapping("/{id}/delete")
  public ResponseEntity<List<Notification>> delete(@PathVariable("id") Long id, Principal principal)
      throws IOException;
}
