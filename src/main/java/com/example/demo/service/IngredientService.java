package com.example.demo.service;

import com.example.demo.entity.IngredientItem;
import com.example.demo.repository.IngredientRepository;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class IngredientService {

  private final IngredientRepository ingredientRepository;


  public List<IngredientItem> getAllIngredients() {
    return ingredientRepository.findAllByOrderByName();
  }

  public Optional<IngredientItem> getIngredientById(Long ingredientId) {
    return ingredientRepository.findAllByIngredientId(ingredientId);
  }

  @Transactional
  public List<IngredientItem> create(String calories,
      String carbohydrates,
      String fat,
      String name,
      String proteins) {
    IngredientItem item = IngredientItem.builder()
        .carbohydrates(Integer.valueOf(carbohydrates))
        .calories(Integer.valueOf(calories))
        .fat(Integer.valueOf(fat))
        .name(name)
        .proteins(Integer.valueOf(proteins))
        .build();
    ingredientRepository.save(item);
    return ingredientRepository.findAllByOrderByName();
  }

  public List<IngredientItem> change(String calories,
      String carbohydrates,
      String fat,
      String name,
      String proteins/*, String oldName*/) {
//    IngredientItem ingredient = ingredientRepository.findByName(oldName).get();
    IngredientItem ingredient = ingredientRepository.findByName(name).get();
    ingredient.setName(name);
    ingredient.setCalories(Integer.valueOf(calories));
    ingredient.setFat(Integer.valueOf(fat));
    ingredient.setCarbohydrates(Integer.valueOf(carbohydrates));
    ingredient.setProteins(Integer.valueOf(proteins));
    ingredientRepository.save(ingredient);
    return ingredientRepository.findAllByOrderByName();
  }

  @Transactional
  public List<IngredientItem> delete(String name) {
    IngredientItem ingredient = ingredientRepository.findByName(name).get();
    ingredientRepository.delete(ingredient);
    return ingredientRepository.findAllByOrderByName();
  }
}
