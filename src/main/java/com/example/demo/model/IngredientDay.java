package com.example.demo.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IngredientDay {

  private Long id;
  private Ingredient ingredient;
  private Integer count;
}
