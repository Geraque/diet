package com.example.demo.controller;

import com.example.demo.api.PlanApi;
import com.example.demo.entity.PlanItem;
import com.example.demo.entity.enums.EatingTime;
import com.example.demo.facade.PlanFacade;
import com.example.demo.model.Day;
import com.example.demo.model.Ingredient;
import com.example.demo.model.Plan;
import com.example.demo.service.PlanService;
import java.security.Principal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Log4j2
public class PlanController implements PlanApi {

  private final PlanService planService;
  private final PlanFacade planFacade;

  @Override
  public ResponseEntity<List<Plan>> getPlansByUserId(String userId) {
    List<Plan> plans = planService.getPlansForUser(Long.valueOf(userId))
        .stream()
        .map(planFacade::apply)
        .collect(Collectors.toList());

    return new ResponseEntity<>(plans, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<List<Plan>> getPlansByCurrentUser(Principal principal) {
    List<Plan> plans = planService.getPlansForUser(principal)
        .stream()
        .map(planFacade::apply)
        .collect(Collectors.toList());

    return new ResponseEntity<>(plans, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<Object> createPlan(String name,
      Principal principal) {
    PlanItem planItem = planService.createPlan(name, principal);
    Plan createdPlan = planFacade.apply(planItem);

    return new ResponseEntity<>(createdPlan, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<Object> copy(String planId, String copyPlanId,
      Principal principal) {
    PlanItem planItem = planService.copy(Long.valueOf(planId), Long.valueOf(copyPlanId), principal);
    Plan createdPlan = planFacade.apply(planItem);

    return new ResponseEntity<>(createdPlan, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<Object> publish(String planId,
      String username,
      String week,
      String date,
      Principal principal) {
    PlanItem planItem = planService.publish(Long.valueOf(planId), username, Integer.valueOf(week),
        LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd")), principal);
    Plan createdPlan = planFacade.apply(planItem);

    return new ResponseEntity<>(createdPlan, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<Object> addIngredient(Principal principal,
      Long planId,
      DayOfWeek dayOfWeek,
      EatingTime eatingTime,
      String ingredient,
      String count) {
    PlanItem planItem = planService.addIngredient(principal, planId, dayOfWeek, eatingTime,
        ingredient, Integer.valueOf(count));
    Plan createdPlan = planFacade.apply(planItem);

    return new ResponseEntity<>(createdPlan, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<Object> deleteIngredient(Principal principal,
      Long planId,
      DayOfWeek dayOfWeek,
      EatingTime eatingTime,
      String ingredient) {
    PlanItem planItem = planService.deleteIngredient(principal, planId, dayOfWeek, eatingTime,
        ingredient);
    Plan createdPlan = planFacade.apply(planItem);

    return new ResponseEntity<>(createdPlan, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<Object> addIngredientReal(Principal principal,
      Long planId,
      DayOfWeek dayOfWeek,
      EatingTime eatingTime,
      String ingredient,
      String count,
      String date) {
    PlanItem planItem = planService.addIngredientReal(principal, planId, dayOfWeek, eatingTime,
        ingredient, Integer.valueOf(count), LocalDate.parse(date));
    Plan createdPlan = planFacade.apply(planItem);

    return new ResponseEntity<>(createdPlan, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<Object> check(Principal principal,
      Long planId,
      DayOfWeek dayOfWeek,
      EatingTime eatingTime,
      String ingredient,
      String count) {
    PlanItem planItem = planService.check(principal, planId, dayOfWeek, eatingTime,
        ingredient, Integer.valueOf(count));
    Plan createdPlan = planFacade.apply(planItem);

    return new ResponseEntity<>(createdPlan, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<Object> checkReal(Principal principal,
      Long planId,
      DayOfWeek dayOfWeek,
      EatingTime eatingTime,
      String ingredient,
      String count,
      String date) {
    PlanItem planItem = planService.checkReal(principal, planId, dayOfWeek, eatingTime,
        ingredient, Integer.valueOf(count), LocalDate.parse(date));
    Plan createdPlan = planFacade.apply(planItem);

    return new ResponseEntity<>(createdPlan, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<Object> update(Principal principal,
      Long planId,
      DayOfWeek dayOfWeek,
      EatingTime eatingTime,
      String ingredientOld,
      String ingredientNew,
      String count,
      String comment) {
    PlanItem planItem = planService.update(principal, planId, dayOfWeek, eatingTime,
        ingredientOld, ingredientNew, Integer.valueOf(count), comment);
    Plan createdPlan = planFacade.apply(planItem);

    return new ResponseEntity<>(createdPlan, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<Object> updateReal(Principal principal,
      Long planId,
      DayOfWeek dayOfWeek,
      EatingTime eatingTime,
      String ingredientOld,
      String ingredientNew,
      String count,
      String comment,
      String date) {
    PlanItem planItem = planService.updateReal(principal, planId, dayOfWeek, eatingTime,
        ingredientOld, ingredientNew, Integer.valueOf(count), comment, LocalDate.parse(date));
    Plan createdPlan = planFacade.apply(planItem);

    return new ResponseEntity<>(createdPlan, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<List<Day>> getToday(Principal principal){
    List<Day> today = planService.getToday(principal).stream()
        .map(planFacade::apply)
        .collect(Collectors.toList());;

    return new ResponseEntity<>(today, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<List<Ingredient>> delete(String name) {
    planService.delete(name);
//    List<Ingredient> ingredients = planService.delete(name)
//        .stream()
//        .map(planFacade::apply)
//        .collect(Collectors.toList());

    return new ResponseEntity<>(null, HttpStatus.OK);
  }
}
