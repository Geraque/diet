package com.example.demo.api;

import com.example.demo.model.Ingredient;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("api/ingredient")
@CrossOrigin
public interface IngredientApi {

  @GetMapping("/")
  public ResponseEntity<List<Ingredient>> getAllIngredients();

  @GetMapping("/{ingredientId}")
  public ResponseEntity<Ingredient> getIngredientById(
      @PathVariable("ingredientId") String ingredientId
  );

  @PostMapping("/create")
  public ResponseEntity<List<Ingredient>> create(
      @RequestParam("calories") String calories,
      @RequestParam(value = "carbohydrates") String carbohydrates,
      @RequestParam(value = "fat") String fat,
      @RequestParam(value = "name") String name,
      @RequestParam(value = "proteins") String proteins
  );

}
