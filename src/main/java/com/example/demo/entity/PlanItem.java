package com.example.demo.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "plans")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlanItem {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "plan_id", nullable = false)
  private Long planId;
  private String name;
  @ManyToOne(fetch = FetchType.LAZY)
  private UserItem user;
  @OneToOne(cascade = CascadeType.ALL, mappedBy = "plan", orphanRemoval = true)
  private FollowerItem follower;
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "plan", orphanRemoval = true)
  private List<DayItem> days = new ArrayList<>();

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "plan", orphanRemoval = true)
  private List<RealDayItem> realDays = new ArrayList<>();

  private Boolean ready;

  @Override
  public String toString() {
    return "PlanItem{" +
        "planId=" + planId +
        ", name='" + name + '\'' +
        ", user=" + user +
        '}';
  }
}
