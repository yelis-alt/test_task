package com.example.test_task.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.cache.annotation.Cacheable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Cacheable("phone_data")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "phone_data", schema = "business")
public class PhoneDataEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "user_id")
    private Long userId;

    @NotBlank
    @Column(name = "phone")
    private String phone;

    public PhoneDataEntity(Long userId, String phone) {
        this.userId = userId;
        this.phone = phone;
    }
}
