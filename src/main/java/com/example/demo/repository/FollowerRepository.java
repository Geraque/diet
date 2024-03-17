package com.example.demo.repository;

import com.example.demo.entity.FollowerItem;
import com.example.demo.entity.PlanItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowerRepository extends JpaRepository<FollowerItem, Long> {

  FollowerItem findByUserId(Long userId);

  FollowerItem findByPlan(PlanItem planItem);
}
