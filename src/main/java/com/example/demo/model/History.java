package com.example.demo.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class History {
  private Long id;
  private Ingredient ingredientNew;
  private Integer countNew;
  private Ingredient ingredientOld;
  private Integer countOld;
}
