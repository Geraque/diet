package com.example.demo.model;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Plan {

  private Long planId;
  private String name;
  private List<Day> days;
  private List<RealDay> realDays;
  private Boolean ready;
}
