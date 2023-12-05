package ru.spbmtsb.cashback.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
public class Bonus {
    @Id
    @GeneratedValue
    private Long id;

    private BigDecimal bonus;

    @OneToOne
    private Customer customer;
}
