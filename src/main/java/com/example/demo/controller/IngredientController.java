package com.example.demo.controller;

import com.example.demo.api.IngredientApi;
import com.example.demo.facade.IngredientFacade;
import com.example.demo.model.Ingredient;
import com.example.demo.service.IngredientService;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Log4j2
public class IngredientController implements IngredientApi {

  private final IngredientService ingredientService;
  private final IngredientFacade ingredientFacade;

  @Override
  public ResponseEntity<List<Ingredient>> getAllIngredients(Principal principal) {
    List<Ingredient> ingredients = ingredientService.getAllIngredients(principal)
        .stream()
        .map(ingredientFacade::ingredientItemToIngredient)
        .collect(Collectors.toList());

    return new ResponseEntity<>(ingredients, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<List<Ingredient>> create(Principal principal, String calories,
      String carbohydrates,
      String fat,
      String name,
      String proteins) {
    List<Ingredient> ingredients = ingredientService.create(principal, calories, carbohydrates, fat,
            name,
            proteins)
        .stream()
        .map(ingredientFacade::ingredientItemToIngredient)
        .collect(Collectors.toList());

    return new ResponseEntity<>(ingredients, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<List<Ingredient>> change(Principal principal, String calories,
      String carbohydrates,
      String fat,
      String name,
      String proteins/*,String oldName*/) {
    List<Ingredient> ingredients = ingredientService.change(principal, calories, carbohydrates, fat,
            name,
            proteins/*, oldName*/)
        .stream()
        .map(ingredientFacade::ingredientItemToIngredient)
        .collect(Collectors.toList());

    return new ResponseEntity<>(ingredients, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<List<Ingredient>> delete(Principal principal, String name) {
    List<Ingredient> ingredients = ingredientService.delete(principal, name)
        .stream()
        .map(ingredientFacade::ingredientItemToIngredient)
        .collect(Collectors.toList());

    return new ResponseEntity<>(ingredients, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<Ingredient> getIngredientById(String ingredientId) {

    return ingredientService.getIngredientById(Long.valueOf(ingredientId))
        .map(ingredientFacade::ingredientItemToIngredient)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }
}
