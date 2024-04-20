package com.example.demo.repository;

import com.example.demo.entity.IngredientItem;
import com.example.demo.entity.RealDayItem;
import com.example.demo.entity.RealHistoryItem;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RealHistoryRepository extends JpaRepository<RealHistoryItem, Long> {

  Optional<RealHistoryItem> findByDayAndIngredientNew(RealDayItem day,
      IngredientItem ingredientNew);
}
