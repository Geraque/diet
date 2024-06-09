package com.example.demo.controller;

import com.example.demo.api.PrintApi;
import com.example.demo.entity.PlanItem;
import com.example.demo.service.PlanService;
import com.example.demo.service.PrintService;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Log4j2
public class PrintController implements PrintApi {
  private final PlanService planService;
  private final PrintService printService;

  @Override
  public ResponseEntity<byte[]> printPlan(String planId) {
    PlanItem plan = planService.getPlanById(Long.valueOf(planId));
    byte[] file = printService.printPlan(plan);
    return new ResponseEntity<>(file, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<byte[]> printWeek(String planId, String startWeek, String endWeek) {
    PlanItem plan = planService.getPlanById(Long.valueOf(planId));
    byte[] file = printService.printWeek(plan,
        LocalDate.parse(startWeek, DateTimeFormatter.ofPattern("yyyy-MM-dd")),
        LocalDate.parse(endWeek, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    return new ResponseEntity<>(file, HttpStatus.OK);
  }

  @Override
  public ResponseEntity<byte[]> printPlanReal(String planId) {
    PlanItem plan = planService.getPlanById(Long.valueOf(planId));
    byte[] file = printService.printPlanReal(plan);
    return new ResponseEntity<>(file, HttpStatus.OK);
  }
}
