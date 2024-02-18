package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "ingredients")
@AllArgsConstructor
@NoArgsConstructor
public class IngredientItem {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "ingredient_id", nullable = false)
  private Long ingredientId;
  @Column(nullable = false)
  private String name;
  private Integer proteins;
  private Integer fat;
  private Integer carbohydrates;
  private Integer calories;
}
