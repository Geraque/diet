package com.example.demo.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Result {

  private Long planId;
  private Long userId;
  private Integer proteins;
  private Integer fat;
  private Integer carbohydrates;
  private Integer calories;
  private Integer change;
  private Integer approved;
}
