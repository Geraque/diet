package com.example.demo.facade;

import com.example.demo.entity.DayItem;
import com.example.demo.entity.IngredientDayItem;
import com.example.demo.entity.IngredientItem;
import com.example.demo.entity.PlanItem;
import com.example.demo.entity.UserItem;
import com.example.demo.model.Day;
import com.example.demo.model.Ingredient;
import com.example.demo.model.IngredientDay;
import com.example.demo.model.Plan;
import com.example.demo.model.User;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class PlanFacade {

  public Plan apply(PlanItem planItem) {
    List<Day> appliedDays = planItem.getDays().stream()
        .map(this::apply)
        .collect(Collectors.toList());

    return Plan.builder()
        .planId(planItem.getPlanId())
        .name(planItem.getName())
        .days(appliedDays)
        .build();
  }

  public User apply(UserItem userItem) {
    User user = new User();
    user.setUserId(userItem.getUserId());
    user.setFirstname(userItem.getName());
    user.setLastname(userItem.getLastname());
    user.setUsername(userItem.getUsername());
    return user;
  }

  //  public PlanWithDays apply(PlanItem planItem, List<DayItem> days) {
//    List<Day> appliedDays = days.stream()
//        .map(this::apply)
//        .collect(Collectors.toList());
//
//    return PlanWithDays.builder()
//        .userId(planItem.getUser())
//        .planId(planItem.getPlanId())
//        .name(planItem.getName())
//        .days(appliedDays)
//        .build();
//  }
//
  public Day apply(DayItem dayItem) {
    List<IngredientDay> appliedIngredients = dayItem.getIngredients().stream()
        .map(this::apply)
        .collect(Collectors.toList());

    return Day.builder()
        .dayId(dayItem.getDayId())
        .day(dayItem.getDay())
        .eatingTime(dayItem.getEatingTime())
        .ingredients(appliedIngredients)
        .build();
  }

  public IngredientDay apply(IngredientDayItem ingredientDayItem) {
    return IngredientDay.builder()
        .count(ingredientDayItem.getCount())
        .id(ingredientDayItem.getId())
        .ingredient(apply(ingredientDayItem.getIngredient()))
        .build();
  }

  public Ingredient apply(IngredientItem ingredientItem) {
    return Ingredient.builder()
        .ingredientId(ingredientItem.getIngredientId())
        .name(ingredientItem.getName())
        .fat(ingredientItem.getFat())
        .calories(ingredientItem.getCalories())
        .carbohydrates(ingredientItem.getCarbohydrates())
        .proteins(ingredientItem.getProteins())
        .build();
  }

}
