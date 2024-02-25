package com.example.demo.repository;

import com.example.demo.entity.DayItem;
import com.example.demo.entity.PlanItem;
import com.example.demo.entity.enums.EatingTime;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DayRepository extends JpaRepository<DayItem, Long> {

  List<DayItem> findAllByPlanOrderByDay(PlanItem plan);

  Optional<DayItem> findByPlanAndDayAndEatingTime(PlanItem plan, DayOfWeek day, EatingTime eatingTime);
}
