package com.example.test_task.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cache;
import org.springframework.cache.annotation.Cacheable;

import java.math.BigDecimal;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Cacheable("account")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "account", schema = "business")
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "user_id")
    private Long userId;

    @NotNull
    @Column(name = "initial_balance")
    private BigDecimal initialBalance;

    @NotNull
    @Column(name = "balance")
    private BigDecimal balance;
}
