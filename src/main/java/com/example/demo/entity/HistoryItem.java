package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "histories")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistoryItem {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "id", nullable = false)
  private Long id;
  @ManyToOne(fetch = FetchType.LAZY)
  private IngredientItem ingredientNew;
  @ManyToOne(fetch = FetchType.LAZY)
  private IngredientItem ingredientOld;
  @ManyToOne(fetch = FetchType.LAZY)
  private DayItem day;
  private Integer countNew;
  private Integer countOld;
}
