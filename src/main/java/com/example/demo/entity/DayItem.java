package com.example.demo.entity;

import com.example.demo.entity.enums.EatingTime;
import java.time.DayOfWeek;
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
@Table(name = "days")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DayItem {
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

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "day", orphanRemoval = true)
  private List<IngredientDayItem> ingredients = new ArrayList<>();

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "day", orphanRemoval = true)
  private List<HistoryItem> history = new ArrayList<>();

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "day", orphanRemoval = true)
  private List<RealHistoryItem> realHistory = new ArrayList<>();
}
