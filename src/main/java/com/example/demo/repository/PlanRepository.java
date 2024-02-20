package com.example.demo.repository;

import com.example.demo.entity.PlanItem;
import com.example.demo.entity.UserItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanRepository extends JpaRepository<PlanItem, Long> {

  List<PlanItem> findAllByUserOrderByName(UserItem user);
}
