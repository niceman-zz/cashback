package ru.spbmtsb.cashback.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.spbmtsb.cashback.constants.CashbackType;
import ru.spbmtsb.cashback.constants.PriceType;
import ru.spbmtsb.cashback.constants.ShopType;
import ru.spbmtsb.cashback.service.cache.CashbackTypesCache;
import ru.spbmtsb.cashback.service.cache.PriceThresholdCache;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CashbackServiceTest {
    @Mock
    private CashbackTypesCache cashbackTypesCache;
    @Mock
    private PriceThresholdCache priceThresholdCache;
    @InjectMocks
    private CashbackService cashbackService;

    private final BigDecimal smallPurchaseThreshold = BigDecimal.valueOf(10);
    private final BigDecimal bigPurchaseThreshold = BigDecimal.valueOf(300);

    @Test
    void getCashbackAmount() {
        BigDecimal onlineCashback = BigDecimal.valueOf(0.1);
        when(cashbackTypesCache.getCashbackByType(CashbackType.ONLINE)).thenReturn(onlineCashback);
        BigDecimal cashBack = cashbackService.getCashbackMultiplier(CashbackType.ONLINE);
        assertEquals(onlineCashback, cashBack);
    }

    @Test
    void getCashbackType_ShopPriceIsSmallerThanSmallPurchase() {
        BigDecimal price = BigDecimal.valueOf(5);

        when(priceThresholdCache.getPriceByType(PriceType.SMALL_PURCHASE)).thenReturn(smallPurchaseThreshold);
        CashbackType cashbackType = cashbackService.getCashbackType(price, ShopType.SHOP);
        assertEquals(CashbackType.SMALL_PURCHASE, cashbackType);
    }

    @Test
    void getCashbackType_OnlinePriceIsSmallerThanSmallPurchase() {
        BigDecimal price = BigDecimal.valueOf(5);

        when(priceThresholdCache.getPriceByType(PriceType.SMALL_PURCHASE)).thenReturn(smallPurchaseThreshold);
        CashbackType cashbackType = cashbackService.getCashbackType(price, ShopType.ONLINE);
        assertEquals(CashbackType.SMALL_PURCHASE, cashbackType);
    }

    @Test
    void getCashbackType_ShopPriceIsGreaterThanBigPurchase() {
        BigDecimal price = BigDecimal.valueOf(305);

        when(priceThresholdCache.getPriceByType(PriceType.SMALL_PURCHASE)).thenReturn(smallPurchaseThreshold);
        when(priceThresholdCache.getPriceByType(PriceType.BIG_PURCHASE)).thenReturn(bigPurchaseThreshold);
        CashbackType cashbackType = cashbackService.getCashbackType(price, ShopType.SHOP);
        assertEquals(CashbackType.BIG_PURCHASE, cashbackType);
    }

    @Test
    void getCashbackType_OnlinePriceIsGreaterThanBigPurchase() {
        BigDecimal price = BigDecimal.valueOf(305);

        when(priceThresholdCache.getPriceByType(PriceType.SMALL_PURCHASE)).thenReturn(smallPurchaseThreshold);
        when(priceThresholdCache.getPriceByType(PriceType.BIG_PURCHASE)).thenReturn(bigPurchaseThreshold);
        CashbackType cashbackType = cashbackService.getCashbackType(price, ShopType.ONLINE);
        assertEquals(CashbackType.BIG_PURCHASE, cashbackType);
    }

    @Test
    void getCashbackType_defaultShopCashback() {
        BigDecimal price = BigDecimal.valueOf(150);

        when(priceThresholdCache.getPriceByType(PriceType.SMALL_PURCHASE)).thenReturn(smallPurchaseThreshold);
        when(priceThresholdCache.getPriceByType(PriceType.BIG_PURCHASE)).thenReturn(bigPurchaseThreshold);
        CashbackType cashbackType = cashbackService.getCashbackType(price, ShopType.SHOP);
        assertEquals(CashbackType.SHOP, cashbackType);
    }

    @Test
    void getCashbackType_defaultOnlineCashback() {
        BigDecimal price = BigDecimal.valueOf(150);

        when(priceThresholdCache.getPriceByType(PriceType.SMALL_PURCHASE)).thenReturn(smallPurchaseThreshold);
        when(priceThresholdCache.getPriceByType(PriceType.BIG_PURCHASE)).thenReturn(bigPurchaseThreshold);
        CashbackType cashbackType = cashbackService.getCashbackType(price, ShopType.ONLINE);
        assertEquals(CashbackType.ONLINE, cashbackType);
    }
}