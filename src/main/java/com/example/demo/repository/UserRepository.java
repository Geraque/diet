package com.example.demo.repository;

import com.example.demo.entity.UserItem;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserItem, Long> {

  Optional<UserItem> findUserItemByUsername(String username);

  Optional<UserItem> findUserItemByEmail(String email);

  Optional<UserItem> findUserItemByUserId(Long userId);


}
