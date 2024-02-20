package com.example.demo.repository;

import com.example.demo.entity.DayItem;
import com.example.demo.entity.IngredientDayItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientDayRepository extends JpaRepository<IngredientDayItem, Long> {

  List<IngredientDayItem> findAllByDayOrderByDay(DayItem day);
}
