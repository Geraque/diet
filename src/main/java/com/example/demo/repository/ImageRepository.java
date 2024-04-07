package com.example.demo.repository;

import com.example.demo.entity.ImageItem;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<ImageItem, Long> {

  Optional<ImageItem> findByUserId(Long userId);

  Optional<ImageItem> findByRecipeId(Long recipeId);
}
