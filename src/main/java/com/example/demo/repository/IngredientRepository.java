package com.example.demo.repository;

import com.example.demo.entity.IngredientItem;
import com.example.demo.entity.UserItem;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends JpaRepository<IngredientItem, Long> {

  List<IngredientItem> findAllByUserOrderByName(UserItem user);

  Optional<IngredientItem> findAllByIngredientId(Long ingredientId);

  Optional<IngredientItem> findByNameAndUser(String name, UserItem user);
}
