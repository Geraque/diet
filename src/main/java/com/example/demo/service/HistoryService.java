package com.example.demo.service;

import com.example.demo.entity.DayItem;
import com.example.demo.entity.HistoryItem;
import com.example.demo.entity.IngredientItem;
import com.example.demo.entity.PlanItem;
import com.example.demo.entity.RealDayItem;
import com.example.demo.entity.RealHistoryItem;
import com.example.demo.entity.UserItem;
import com.example.demo.entity.enums.EatingTime;
import com.example.demo.repository.DayRepository;
import com.example.demo.repository.HistoryRepository;
import com.example.demo.repository.IngredientRepository;
import com.example.demo.repository.PlanRepository;
import com.example.demo.repository.RealDayRepository;
import com.example.demo.repository.RealHistoryRepository;
import com.example.demo.repository.UserRepository;
import java.security.Principal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class HistoryService {
  private final PlanRepository planRepository;
  private final DayRepository dayRepository;
  private final RealDayRepository realDayRepository;
  private final HistoryRepository historyRepository;
  private final RealHistoryRepository realHistoryRepository;
  private final IngredientRepository ingredientRepository;
  private final UserRepository userRepository;

  public HistoryItem last(Principal principal, Long planId, DayOfWeek dayOfWeek,
      EatingTime eatingTime,
      String ingredient) {
    UserItem user = getUserByPrincipal(principal);
    PlanItem plan = planRepository.findByPlanId(planId).get();
    DayItem day = dayRepository.findByPlanAndDayAndEatingTime(plan, dayOfWeek, eatingTime).get();
    IngredientItem ingredientItem = ingredientRepository.findByNameAndUser(ingredient, user).get();
    return historyRepository.findByDayAndIngredientNew(day, ingredientItem).get();
  }

  public RealHistoryItem lastReal(Principal principal, Long planId, DayOfWeek dayOfWeek,
      EatingTime eatingTime,
      String ingredient, LocalDate date) {
    UserItem user = getUserByPrincipal(principal);
    PlanItem plan = planRepository.findByPlanId(planId).get();
    RealDayItem day = realDayRepository.findByPlanAndDayAndEatingTimeAndDate(plan, dayOfWeek,
            eatingTime, date)
        .get();
    IngredientItem ingredientItem = ingredientRepository.findByNameAndUser(ingredient, user).get();
    return realHistoryRepository.findByDayAndIngredientNew(day, ingredientItem).get();
  }


  private UserItem getUserByPrincipal(Principal principal) {
    String username = principal.getName();
    return userRepository.findUserItemByUsername(username)
        .orElseThrow(
            () -> new UsernameNotFoundException("Username not found with username " + username));

  }
}
