package com.example.demo.service;

import com.example.demo.entity.DayItem;
import com.example.demo.entity.HistoryItem;
import com.example.demo.entity.IngredientItem;
import com.example.demo.entity.PlanItem;
import com.example.demo.entity.enums.EatingTime;
import com.example.demo.repository.DayRepository;
import com.example.demo.repository.HistoryRepository;
import com.example.demo.repository.IngredientRepository;
import com.example.demo.repository.PlanRepository;
import java.time.DayOfWeek;
import java.util.Comparator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class HistoryService {


  private final PlanRepository planRepository;
  private final DayRepository dayRepository;
  private final HistoryRepository historyRepository;
  private final IngredientRepository ingredientRepository;

  public HistoryItem last(Long planId, DayOfWeek dayOfWeek, EatingTime eatingTime, String ingredient) {
    PlanItem plan = planRepository.findByPlanId(planId).get();
    DayItem day = dayRepository.findByPlanAndDayAndEatingTime(plan, dayOfWeek, eatingTime).get();
    IngredientItem ingredientItem = ingredientRepository.findByName(ingredient).get();
    return historyRepository.findByDayAndIngredientNew(day, ingredientItem).get();
  }
}
