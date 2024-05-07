package com.example.demo.api;

import com.example.demo.entity.enums.EatingTime;
import com.example.demo.model.Day;
import com.example.demo.model.Plan;
import com.example.demo.model.Result;
import java.security.Principal;
import java.time.DayOfWeek;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("api/plan")
@CrossOrigin
public interface PlanApi {

  @GetMapping("/{userId}")
  public ResponseEntity<List<Plan>> getPlansByUserId(
      @PathVariable("userId") String userId
  );

  @GetMapping("/all")
  public ResponseEntity<List<Plan>> getPlansByCurrentUser(Principal principal);

  @PostMapping("/create")
  public ResponseEntity<Object> createPlan(
      @RequestParam(value = "name") String name,
      Principal principal);


  @PostMapping("/copy")
  public ResponseEntity<Object> copy(
      @RequestParam(value = "planId") String planId,
      @RequestParam(value = "copyPlanId") String copyPlanId,
      Principal principal);

  @PostMapping("/ready")
  public ResponseEntity<Object> ready(
      @RequestParam(value = "planId") String planId,
      @RequestParam(value = "userName") String userName,
      @RequestParam(value = "week") String week,
      @RequestParam(value = "date") String date,
      Principal principal);

  @PostMapping("/{planId}/{dayOfWeek}/{eatingTime}/ingredient")
  public ResponseEntity<Object> addIngredient(
      Principal principal,
      @PathVariable("planId") Long planId,
      @PathVariable("dayOfWeek") DayOfWeek dayOfWeek,
      @PathVariable("eatingTime") EatingTime eatingTime,
      @RequestParam(value = "ingredient") String ingredient,
      @RequestParam(value = "count") String count
  );

  @DeleteMapping("/{planId}/{dayOfWeek}/{eatingTime}/{ingredient}/delete")
  public ResponseEntity<Object> deleteIngredient(
      Principal principal,
      @PathVariable("planId") Long planId,
      @PathVariable("dayOfWeek") DayOfWeek dayOfWeek,
      @PathVariable("eatingTime") EatingTime eatingTime,
      @PathVariable("ingredient") String ingredient
  );

  @PostMapping("/{planId}/{dayOfWeek}/{eatingTime}/ingredient/real")
  public ResponseEntity<Object> addIngredientReal(
      Principal principal,
      @PathVariable("planId") Long planId,
      @PathVariable("dayOfWeek") DayOfWeek dayOfWeek,
      @PathVariable("eatingTime") EatingTime eatingTime,
      @RequestParam(value = "ingredient") String ingredient,
      @RequestParam(value = "count") String count,
      @RequestParam(value = "date") String date
  );

  @PostMapping("/{planId}/{dayOfWeek}/{eatingTime}/check")
  public ResponseEntity<Object> check(
      Principal principal,
      @PathVariable("planId") Long planId,
      @PathVariable("dayOfWeek") DayOfWeek dayOfWeek,
      @PathVariable("eatingTime") EatingTime eatingTime,
      @RequestParam(value = "ingredient") String ingredient,
      @RequestParam(value = "count") String count
  );

  @PostMapping("/{planId}/{dayOfWeek}/{eatingTime}/check/real")
  public ResponseEntity<Object> checkReal(
      Principal principal,
      @PathVariable("planId") Long planId,
      @PathVariable("dayOfWeek") DayOfWeek dayOfWeek,
      @PathVariable("eatingTime") EatingTime eatingTime,
      @RequestParam(value = "ingredient") String ingredient,
      @RequestParam(value = "count") String count,
      @RequestParam(value = "date") String date
  );

  @PostMapping("/{planId}/{dayOfWeek}/{eatingTime}/update")
  public ResponseEntity<Object> update(
      Principal principal,
      @PathVariable("planId") Long planId,
      @PathVariable("dayOfWeek") DayOfWeek dayOfWeek,
      @PathVariable("eatingTime") EatingTime eatingTime,
      @RequestParam(value = "ingredientOld") String ingredientOld,
      @RequestParam(value = "ingredientNew") String ingredientNew,
      @RequestParam(value = "count") String count,
      @RequestParam(value = "comment") String comment
  );

  @PostMapping("/{planId}/{dayOfWeek}/{eatingTime}/update/real")
  public ResponseEntity<Object> updateReal(
      Principal principal,
      @PathVariable("planId") Long planId,
      @PathVariable("dayOfWeek") DayOfWeek dayOfWeek,
      @PathVariable("eatingTime") EatingTime eatingTime,
      @RequestParam(value = "ingredientOld") String ingredientOld,
      @RequestParam(value = "ingredientNew") String ingredientNew,
      @RequestParam(value = "count") String count,
      @RequestParam(value = "comment") String comment,
      @RequestParam(value = "date") String date
  );

  @GetMapping("/today")
  public ResponseEntity<List<Day>> getToday(Principal principal);

  @GetMapping("/result")
  public ResponseEntity<Result> getResult(Principal principal);
}
