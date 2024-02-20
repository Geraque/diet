package com.example.demo.model;

import com.example.demo.entity.enums.EatingTime;
import java.time.DayOfWeek;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Day {

  private Long dayId;
  private DayOfWeek day;
  private EatingTime eatingTime;
  private List<IngredientDay> ingredients;
}
