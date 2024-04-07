package com.example.demo.api;

import com.example.demo.entity.enums.EatingTime;
import com.example.demo.model.Day;
import com.example.demo.model.Plan;
import java.security.Principal;
import java.time.DayOfWeek;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
  public ResponseEntity<Object> createPlan(@Valid @RequestBody Plan plan,
      BindingResult bindingResult,
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

  @PostMapping("/{planId}/{dayOfWeek}/{eatingTime}/ingredient/real")
  public ResponseEntity<Object> addIngredientReal(
      Principal principal,
      @PathVariable("planId") Long planId,
      @PathVariable("dayOfWeek") DayOfWeek dayOfWeek,
      @PathVariable("eatingTime") EatingTime eatingTime,
      @RequestParam(value = "ingredient") String ingredient,
      @RequestParam(value = "count") String count
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

  @GetMapping("/today")
  public ResponseEntity<List<Day>> getToday(Principal principal);
}
