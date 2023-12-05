package ru.spbmtsb.cashback.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
public class Account {
    @Id
    @GeneratedValue
    private Long id;

    private BigDecimal money;

    @ManyToOne
    private Customer customer;
}
