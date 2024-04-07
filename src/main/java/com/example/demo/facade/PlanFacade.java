package com.example.demo.facade;

import com.example.demo.entity.DayItem;
import com.example.demo.entity.HistoryItem;
import com.example.demo.entity.IngredientDayItem;
import com.example.demo.entity.IngredientItem;
import com.example.demo.entity.IngredientRealDayItem;
import com.example.demo.entity.NotificationItem;
import com.example.demo.entity.PlanItem;
import com.example.demo.entity.RealDayItem;
import com.example.demo.entity.UserItem;
import com.example.demo.model.Day;
import com.example.demo.model.History;
import com.example.demo.model.Ingredient;
import com.example.demo.model.IngredientDay;
import com.example.demo.model.IngredientRealDay;
import com.example.demo.model.Notification;
import com.example.demo.model.Plan;
import com.example.demo.model.RealDay;
import com.example.demo.model.User;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class PlanFacade {

  public Plan apply(PlanItem planItem) {
    List<Day> appliedDays = new ArrayList<>();
    if (planItem.getDays() != null) {
      appliedDays = planItem.getDays().stream()
          .map(this::apply)
          .collect(Collectors.toList());
    }

    List<RealDay> appliedRealDays = new ArrayList<>();
    if (planItem.getRealDays() != null) {
      appliedRealDays = planItem.getRealDays().stream()
          .map(this::apply)
          .collect(Collectors.toList());
    }
    return Plan.builder()
        .planId(planItem.getPlanId())
        .name(planItem.getName())
        .days(appliedDays)
        .realDays(appliedRealDays)
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

  public RealDay apply(RealDayItem dayItem) {
    List<IngredientRealDay> appliedIngredients = dayItem.getIngredients().stream()
        .map(this::apply)
        .collect(Collectors.toList());

    return RealDay.builder()
        .dayId(dayItem.getDayId())
        .day(dayItem.getDay())
        .eatingTime(dayItem.getEatingTime())
        .ingredients(appliedIngredients)
        .date(dayItem.getDate())
        .build();
  }

  public IngredientDay apply(IngredientDayItem ingredientDayItem) {
    return IngredientDay.builder()
        .count(ingredientDayItem.getCount())
        .id(ingredientDayItem.getId())
        .ingredient(apply(ingredientDayItem.getIngredient()))
        .checkIngredient(ingredientDayItem.getCheckIngredient())
        .build();
  }

  public IngredientRealDay apply(IngredientRealDayItem ingredientDayItem) {
    return IngredientRealDay.builder()
        .count(ingredientDayItem.getCount())
        .id(ingredientDayItem.getId())
        .ingredient(apply(ingredientDayItem.getIngredient()))
        .checkIngredient(ingredientDayItem.getCheckIngredient())
        .build();
  }

  public History apply(HistoryItem historyItem) {
    return History.builder()
        .countOld(historyItem.getCountOld())
        .countNew(historyItem.getCountNew())
        .id(historyItem.getId())
        .ingredientOld(apply(historyItem.getIngredientOld()))
        .ingredientNew(apply(historyItem.getIngredientNew()))
        .comment(historyItem.getComment())
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

  public Notification apply(NotificationItem item) {
    return Notification.builder()
        .id(item.getId())
        .heading(item.getHeading())
        .text(item.getText())
        .date(item.getDate())
        .user(item.getUser().getUserId())
        .isRead(item.getIsRead())
        .build();
  }
}
