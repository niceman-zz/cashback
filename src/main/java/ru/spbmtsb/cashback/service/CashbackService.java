package ru.spbmtsb.cashback.service;

import org.springframework.stereotype.Service;
import ru.spbmtsb.cashback.constants.CashbackType;
import ru.spbmtsb.cashback.constants.PriceType;
import ru.spbmtsb.cashback.constants.ShopType;
import ru.spbmtsb.cashback.service.cache.CashbackTypesCache;
import ru.spbmtsb.cashback.service.cache.PriceThresholdCache;

import java.math.BigDecimal;

@Service
public class CashbackService {
    private final CashbackTypesCache cashbackTypesCache;
    private final PriceThresholdCache priceThresholdCache;

    public CashbackService(CashbackTypesCache cashbackTypesCache, PriceThresholdCache priceThresholdCache) {
        this.cashbackTypesCache = cashbackTypesCache;
        this.priceThresholdCache = priceThresholdCache;
    }

    public BigDecimal getCashbackMultiplier(CashbackType cashbackType) {
        return cashbackTypesCache.getCashbackByType(cashbackType);
    }

    public CashbackType getCashbackType(BigDecimal price, ShopType shopType) {
        if (price.compareTo(priceThresholdCache.getPriceByType(PriceType.SMALL_PURCHASE)) < 0) {
            return CashbackType.SMALL_PURCHASE;
        } else if (price.compareTo(priceThresholdCache.getPriceByType(PriceType.BIG_PURCHASE)) > 0) {
            return CashbackType.BIG_PURCHASE;
        }
        return getShopCashbackType(shopType);
    }

    private CashbackType getShopCashbackType(ShopType shop) {
        return switch (shop) {
            case SHOP -> CashbackType.SHOP;
            case ONLINE -> CashbackType.ONLINE;
        };
    }
}
