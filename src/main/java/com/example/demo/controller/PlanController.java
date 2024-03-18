package com.example.demo.controller;

import com.example.demo.api.PlanApi;
import com.example.demo.entity.DayItem;
import com.example.demo.entity.PlanItem;
import com.example.demo.entity.enums.EatingTime;
import com.example.demo.facade.PlanFacade;
import com.example.demo.model.Day;
import com.example.demo.model.Plan;
import com.example.demo.service.PlanService;
import com.example.demo.validations.ResponseErrorValidation;
import java.security.Principal;
import java.time.DayOfWeek;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Log4j2
public class PlanController implements PlanApi {

  private final PlanService planService;
  private final PlanFacade planFacade;
  private ResponseErrorValidation responseErrorValidation;

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
  public ResponseEntity<Object> createPlan(Plan plan,
      BindingResult bindingResult,
      Principal principal) {
    ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
    if (!ObjectUtils.isEmpty(errors)) {
      return errors;
    }

    PlanItem planItem = planService.createPlan(plan, principal);
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
  public ResponseEntity<List<Day>> getToday(Principal principal){
    List<Day> today = planService.getToday(principal).stream()
        .map(planFacade::apply)
        .collect(Collectors.toList());;

    return new ResponseEntity<>(today, HttpStatus.OK);
  }
}
