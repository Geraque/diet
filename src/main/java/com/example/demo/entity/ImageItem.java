package com.example.demo.entity;

import java.util.Arrays;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.Type;

@Data
@Entity
@Table(name = "images")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageItem {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long imageId;
  @Column(nullable = false)
  private String name;
  @Lob
  @Type(type = "org.hibernate.type.ImageType")
  private byte[] imageBytes;
  @JsonIgnore
  private Long userId;
  @JsonIgnore
  private Long recipeId;

}
