package ru.spbmtsb.cashback.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spbmtsb.cashback.constants.CashbackType;
import ru.spbmtsb.cashback.constants.ShopType;
import ru.spbmtsb.cashback.entity.Bonus;
import ru.spbmtsb.cashback.repo.BonusRepository;

import java.math.BigDecimal;

@Service
public class BonusService {
    private final BonusRepository bonusRepository;
    private final CashbackService cashbackService;

    public BonusService(BonusRepository bonusRepository, CashbackService cashbackService) {
        this.bonusRepository = bonusRepository;
        this.cashbackService = cashbackService;
    }

    @Transactional
    public void addBonus(long customerId, BigDecimal price, ShopType shopType) {
        CashbackType cashbackType = cashbackService.getCashbackType(price, shopType);
        BigDecimal cashbackMultiplier = cashbackService.getCashbackMultiplier(cashbackType);

        BigDecimal cashback = price.multiply(cashbackMultiplier);
        Bonus bonus = bonusRepository.getByCustomerId(customerId);
        bonus.setBonus(bonus.getBonus().add(cashback));
    }

    public BigDecimal getBonusAmount(long customerId) {
        return bonusRepository.getByCustomerId(customerId).getBonus();
    }
}
