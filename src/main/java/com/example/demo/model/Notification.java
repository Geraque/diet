package com.example.demo.model;

import java.time.ZonedDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Notification {

  private Long id;
  private Long user;
  private String heading;
  private String text;
  private ZonedDateTime date;
  private Boolean isRead;
  private Boolean isDeleted;
}
