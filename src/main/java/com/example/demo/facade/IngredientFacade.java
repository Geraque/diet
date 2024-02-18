package com.example.demo.facade;

import com.example.demo.entity.IngredientItem;
import com.example.demo.model.Ingredient;
import org.springframework.stereotype.Component;

@Component
public class IngredientFacade {

  public Ingredient ingredientItemToIngredient(IngredientItem ingredientItem) {
    return Ingredient.builder()
        .ingredientId(ingredientItem.getIngredientId())
        .name(ingredientItem.getName())
        .fat(ingredientItem.getFat())
        .calories(ingredientItem.getCalories())
        .carbohydrates(ingredientItem.getCarbohydrates())
        .proteins(ingredientItem.getProteins())
        .build();
  }

}
