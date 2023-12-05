package ru.spbmtsb.cashback.service.cache;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import ru.spbmtsb.cashback.constants.PriceType;
import ru.spbmtsb.cashback.entity.PriceThreshold;
import ru.spbmtsb.cashback.repo.PriceThresholdRepository;

import java.math.BigDecimal;
import java.util.EnumMap;

/**
 * Кэш для цен, влияющих на кэшбэк
 */
@Service
public class PriceThresholdCache {
    private final PriceThresholdRepository priceThresholdRepository;

    private final EnumMap<PriceType, BigDecimal> priceMap = new EnumMap<>(PriceType.class);

    public PriceThresholdCache(PriceThresholdRepository priceThresholdRepository) {
        this.priceThresholdRepository = priceThresholdRepository;
    }

    @PostConstruct
    public void init() {
        for (PriceThreshold priceThreshold : priceThresholdRepository.findAll()) {
            priceMap.put(priceThreshold.getPriceType(), priceThreshold.getThreshold());
        }
    }

    public BigDecimal getPriceByType(PriceType priceType) {
        return priceMap.get(priceType);
    }
}
