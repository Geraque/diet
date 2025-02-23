package com.example.demo.api;

import com.example.demo.entity.enums.EatingTime;
import java.security.Principal;
import java.time.DayOfWeek;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("api/history")
@CrossOrigin
public interface HistoryApi {

  @PostMapping("/{planId}/{dayOfWeek}/{eatingTime}/last")
  public ResponseEntity<Object> last(Principal principal,
      @PathVariable("planId") Long planId,
      @PathVariable("dayOfWeek") DayOfWeek dayOfWeek,
      @PathVariable("eatingTime") EatingTime eatingTime,
      @RequestParam(value = "ingredientNew") String ingredientNew
  );

  @PostMapping("/{planId}/{dayOfWeek}/{eatingTime}/last/real")
  public ResponseEntity<Object> lastReal(Principal principal,
      @PathVariable("planId") Long planId,
      @PathVariable("dayOfWeek") DayOfWeek dayOfWeek,
      @PathVariable("eatingTime") EatingTime eatingTime,
      @RequestParam(value = "ingredientNew") String ingredientNew,
      @RequestParam(value = "date") String date
  );
}
