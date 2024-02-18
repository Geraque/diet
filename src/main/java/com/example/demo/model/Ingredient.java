package com.example.demo.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Ingredient {

  private Long ingredientId;
  private String name;
  private Integer proteins;
  private Integer fat;
  private Integer carbohydrates;
  private Integer calories;

}
