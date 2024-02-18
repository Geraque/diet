package com.example.demo.entity;

import com.example.demo.entity.enums.ERole;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

//Таблица, которая хранит данные о юзерах
@Data
@Entity
@Table(name = "user_item")
public class UserItem implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "user_id", nullable = false)
  private Long userId;
  @Column(nullable = false)
  private String name;
  @Column(nullable = false)
  private String lastname;
  @Column(unique = true, updatable = false)
  private String username;
  @Column(length = 3000)
  private String password;
  @Column(unique = true)
  private String email;
  @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss")
  @Column(updatable = false)
  private LocalDateTime dateCreated;

  @ElementCollection(targetClass = ERole.class)
  @CollectionTable(name = "user_role",
      joinColumns = @JoinColumn(name = "user_id"))
  private Set<ERole> roles = new HashSet<>();

  @Transient
  private Collection<? extends GrantedAuthority> authorities;

  public UserItem() {
  }

  public UserItem(Long userId,
      String username,
      String email,
      String password,
      Collection<? extends GrantedAuthority> authorities) {
    this.userId = userId;
    this.username = username;
    this.email = email;
    this.password = password;
    this.authorities = authorities;
  }

  @PrePersist
  protected void onCreate() {
    this.dateCreated = LocalDateTime.now();
  }


  /**
   * SECURITY
   */


  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
