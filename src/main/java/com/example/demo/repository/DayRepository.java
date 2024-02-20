package com.example.demo.repository;

import com.example.demo.entity.DayItem;
import com.example.demo.entity.PlanItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DayRepository extends JpaRepository<DayItem, Long> {

  List<DayItem> findAllByPlanOrderByDay(PlanItem plan);
}
