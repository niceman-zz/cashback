package ru.spbmtsb.cashback.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
public class Customer {
    @Id
    @GeneratedValue
    private Long id;

    @OneToMany
    private Set<Account> accounts;

    @OneToOne(mappedBy = "customer")
    private Bonus bonus;
}
