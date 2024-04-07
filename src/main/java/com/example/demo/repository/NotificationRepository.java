package com.example.demo.repository;

import com.example.demo.entity.NotificationItem;
import com.example.demo.entity.UserItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationItem, Long> {

  List<NotificationItem> findAllByUserOrderByDate(UserItem user);
}
