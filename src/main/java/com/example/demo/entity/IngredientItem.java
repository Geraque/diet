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
@Table(name = "ingredients")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IngredientItem {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "ingredient_id", nullable = false)
  private Long ingredientId;
  @Column(nullable = false)
  private String name;
  private Double proteins;
  private Double fat;
  private Double carbohydrates;
  private Double calories;
  @ManyToOne(fetch = FetchType.LAZY)
  private UserItem user;
}
