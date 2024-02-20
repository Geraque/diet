package com.example.demo.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "enter")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnterItem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "enter_id", nullable = false)
    private Long enterId;
    @Column(name = "user_id", nullable = false)
    private Long userId;
    @Column(updatable = false)
    private LocalDateTime dateEntered;

    @PrePersist
    protected void onCreate()
    {
        this.dateEntered = LocalDateTime.now();
    }
}
