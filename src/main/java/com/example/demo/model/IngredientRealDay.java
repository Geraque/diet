package com.example.demo.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IngredientRealDay {

  private Long id;
  private Ingredient ingredient;
  private Integer count;
  private Boolean checkIngredient;
}
