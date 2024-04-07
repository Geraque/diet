package com.example.demo.controller;

import com.example.demo.api.HistoryApi;
import com.example.demo.entity.HistoryItem;
import com.example.demo.entity.enums.EatingTime;
import com.example.demo.facade.PlanFacade;
import com.example.demo.model.History;
import com.example.demo.service.HistoryService;
import java.time.DayOfWeek;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Log4j2
public class HistoryController implements HistoryApi {

  private final HistoryService historyService;
  private final PlanFacade planFacade;

  @Override
  public ResponseEntity<Object> last(Long planId, DayOfWeek dayOfWeek,
      EatingTime eatingTime, String ingredientNew) {
    HistoryItem historyItem = historyService.last(planId, dayOfWeek, eatingTime, ingredientNew);
    History createdHistory = planFacade.apply(historyItem);

    return new ResponseEntity<>(createdHistory, HttpStatus.OK);
  }
}
