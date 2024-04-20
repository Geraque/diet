package com.example.demo.repository;

import com.example.demo.entity.PlanItem;
import com.example.demo.entity.RealDayItem;
import com.example.demo.entity.enums.EatingTime;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RealDayRepository extends JpaRepository<RealDayItem, Long> {

  List<RealDayItem> findAllByPlanOrderByDay(PlanItem plan);

  Optional<RealDayItem> findByPlanAndDayAndEatingTimeAndDate(PlanItem plan, DayOfWeek day,
      EatingTime eatingTime, LocalDate date);
}
