package com.example.corebank.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.math.BigDecimal;

@Entity(name = "accounts")
@Data
public class Account {
    @Id
    private String accountId;

    @Column(name = "money")
    private BigDecimal money;
}
