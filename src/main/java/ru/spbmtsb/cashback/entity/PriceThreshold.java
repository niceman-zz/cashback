package ru.spbmtsb.cashback.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Data;
import ru.spbmtsb.cashback.constants.PriceType;

import java.math.BigDecimal;

@Data
@Entity
public class PriceThreshold {
    @Id
    @Enumerated(EnumType.STRING)
    private PriceType priceType;

    private BigDecimal threshold;
}
