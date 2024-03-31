package com.example.demo.entity;

import com.example.demo.entity.enums.EatingTime;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "real_days")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RealDayItem {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "day_id", nullable = false)
  private Long dayId;
  @ManyToOne(fetch = FetchType.LAZY)
  private PlanItem plan;
  @Enumerated(EnumType.STRING)
  @Column(name = "day")
  private DayOfWeek day;

  @Enumerated(EnumType.STRING)
  @Column(name = "eating_time")
  private EatingTime eatingTime;

  @Column(name = "date")
  private LocalDate date;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "day", orphanRemoval = true)
  private List<IngredientRealDayItem> ingredients = new ArrayList<>();


}
