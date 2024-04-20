package com.example.demo.repository;

import com.example.demo.entity.DayItem;
import com.example.demo.entity.IngredientItem;
import com.example.demo.entity.IngredientRealDayItem;
import com.example.demo.entity.RealDayItem;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRealDayRepository extends JpaRepository<IngredientRealDayItem, Long> {

  List<IngredientRealDayItem> findAllByDayOrderByDay(DayItem day);

  Optional<IngredientRealDayItem> findByDayAndIngredient(RealDayItem day,
      IngredientItem ingredientItem);
}
