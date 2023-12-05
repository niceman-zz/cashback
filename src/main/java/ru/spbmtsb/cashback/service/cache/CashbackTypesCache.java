package ru.spbmtsb.cashback.service.cache;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import ru.spbmtsb.cashback.constants.CashbackType;
import ru.spbmtsb.cashback.entity.Cashback;
import ru.spbmtsb.cashback.repo.CashbackRepository;

import java.math.BigDecimal;
import java.util.EnumMap;

/**
 * Кэш кэшбэков
 */
@Service
public class CashbackTypesCache {
    private static final BigDecimal ONE_HUNDREDTH = BigDecimal.valueOf(0.01);

    private final EnumMap<CashbackType, BigDecimal> cashbacksMap = new EnumMap<>(CashbackType.class);

    private final CashbackRepository cashbackRepository;

    public CashbackTypesCache(CashbackRepository cashbackRepository) {
        this.cashbackRepository = cashbackRepository;
    }

    @PostConstruct
    public void init() {
        for (Cashback cashback : cashbackRepository.findAll()) {
            cashbacksMap.put(cashback.getType(), cashback.getPercent().multiply(ONE_HUNDREDTH));
        }
    }

    public BigDecimal getCashbackByType(CashbackType type) {
        return cashbacksMap.get(type);
    }
}
