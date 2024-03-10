package com.example.demo.repository;

import com.example.demo.entity.DayItem;
import com.example.demo.entity.HistoryItem;
import com.example.demo.entity.IngredientItem;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryRepository extends JpaRepository<HistoryItem, Long> {

  Optional<HistoryItem> findByDayAndIngredientNew(DayItem day, IngredientItem ingredientNew);
}
