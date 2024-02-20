package com.example.demo.api;

import com.example.demo.model.Plan;
import java.security.Principal;
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
}
