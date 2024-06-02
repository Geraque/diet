package com.example.demo.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Ingredient {

  private Long ingredientId;
  private String name;
  private Double proteins;
  private Double fat;
  private Double carbohydrates;
  private Double calories;

}
