package com.example.demo.model;

import com.example.demo.entity.enums.EatingTime;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RealDay {

  private Long dayId;
  private DayOfWeek day;
  private EatingTime eatingTime;
  private List<IngredientRealDay> ingredients;
  private LocalDate date;
}
