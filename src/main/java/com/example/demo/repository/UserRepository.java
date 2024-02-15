package com.example.demo.repository;

import com.example.demo.entity.UserItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserItem, Long> {

    Optional<UserItem> findUserModelByUsername(String username);

    Optional<UserItem> findUserModelByEmail(String email);

    Optional<UserItem> findUserModelByUserId(Long userId);


}
