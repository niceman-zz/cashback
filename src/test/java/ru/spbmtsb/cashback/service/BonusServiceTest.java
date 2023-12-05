package ru.spbmtsb.cashback.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.spbmtsb.cashback.constants.CashbackType;
import ru.spbmtsb.cashback.constants.ShopType;
import ru.spbmtsb.cashback.entity.Bonus;
import ru.spbmtsb.cashback.repo.BonusRepository;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BonusServiceTest {
    @Mock
    private BonusRepository bonusRepository;

    @Mock
    private CashbackService cashbackService;

    @InjectMocks
    private BonusService bonusService;

    @Test
    void addBonus_empty() {
        BigDecimal price = BigDecimal.valueOf(20);
        ShopType shopType = ShopType.ONLINE;
        CashbackType cashbackType = CashbackType.ONLINE;
        BigDecimal cashbackMultiplier = BigDecimal.valueOf(0.1);
        long customerId = 1L;
        Bonus bonus = new Bonus();
        bonus.setBonus(BigDecimal.ZERO);
        BigDecimal expected = price.multiply(cashbackMultiplier);

        when(cashbackService.getCashbackType(price, shopType)).thenReturn(cashbackType);
        when(cashbackService.getCashbackMultiplier(cashbackType)).thenReturn(cashbackMultiplier);
        when(bonusRepository.getByCustomerId(customerId)).thenReturn(bonus);

        bonusService.addBonus(customerId, price, shopType);

        assertEquals(expected, bonus.getBonus());
    }

    @Test
    void addBonus_someValue() {
        BigDecimal price = BigDecimal.valueOf(20);
        ShopType shopType = ShopType.ONLINE;
        CashbackType cashbackType = CashbackType.ONLINE;
        BigDecimal cashbackMultiplier = BigDecimal.valueOf(0.1);
        long customerId = 1L;
        Bonus bonus = new Bonus();
        bonus.setBonus(BigDecimal.valueOf(10.5));
        BigDecimal expected = bonus.getBonus().add(price.multiply(cashbackMultiplier));

        when(cashbackService.getCashbackType(price, shopType)).thenReturn(cashbackType);
        when(cashbackService.getCashbackMultiplier(cashbackType)).thenReturn(cashbackMultiplier);
        when(bonusRepository.getByCustomerId(customerId)).thenReturn(bonus);

        bonusService.addBonus(customerId, price, shopType);

        assertEquals(expected, bonus.getBonus());
    }

    @Test
    void getBonusAmount() {
        Bonus bonus = new Bonus();
        long customerId = 1L;

        when(bonusRepository.getByCustomerId(customerId)).thenReturn(bonus);

        BigDecimal result = bonusService.getBonusAmount(customerId);

        assertEquals(bonus.getBonus(), result);
    }
}