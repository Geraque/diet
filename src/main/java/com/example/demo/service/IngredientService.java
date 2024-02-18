package com.example.demo.service;

import com.example.demo.entity.IngredientItem;
import com.example.demo.repository.IngredientRepository;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class IngredientService {

  private final IngredientRepository ingredientRepository;


  public List<IngredientItem> getAllRecipes() {
    return ingredientRepository.findAllByOrderByName();
  }

  public Optional<IngredientItem> getIngredientById(Long ingredientId) {
    return ingredientRepository.findAllByIngredientId(ingredientId);
  }
}
