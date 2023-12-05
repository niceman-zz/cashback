package ru.spbmtsb.cashback.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Data;
import ru.spbmtsb.cashback.constants.CashbackType;

import java.math.BigDecimal;

@Data
@Entity
public class Cashback {
    @Id
    @Enumerated(EnumType.STRING)
    private CashbackType type;

    private BigDecimal percent;
}
