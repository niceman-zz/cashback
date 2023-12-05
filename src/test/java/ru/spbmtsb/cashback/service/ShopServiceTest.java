package ru.spbmtsb.cashback.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.spbmtsb.cashback.constants.PriceType;
import ru.spbmtsb.cashback.constants.ShopType;
import ru.spbmtsb.cashback.service.cache.PriceThresholdCache;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShopServiceTest {
    private static final long CUSTOMER_ID = 1L;
    private static final long ACCOUNT_ID = 1L;

    @Mock
    private AccountService accountService;
    @Mock
    private BonusService bonusService;
    @Mock
    private PriceThresholdCache priceThresholdCache;

    @InjectMocks
    private ShopService shopService;

    @Test
    void shopPayment_returnIsNotIncrementedWhenPriceIsBigEnough() {
        BigDecimal price = BigDecimal.TEN;

        doNothing().when(accountService).withdrawMoney(ACCOUNT_ID, price);
        doNothing().when(bonusService).addBonus(CUSTOMER_ID, price, ShopType.SHOP);
        when(priceThresholdCache.getPriceByType(PriceType.SMALL_PURCHASE)).thenReturn(BigDecimal.ONE);

        shopService.shopPayment(CUSTOMER_ID, ACCOUNT_ID, price);

        verify(accountService, times(1)).withdrawMoney(ACCOUNT_ID, price);
        verify(bonusService, times(1)).addBonus(CUSTOMER_ID, price, ShopType.SHOP);

        assertEquals(0, shopService.getReturnCounter());
    }

    @Test
    void shopPayment_returnIsIncrementedWhenPriceIsNotBigEnough() {
        BigDecimal price = BigDecimal.ONE;

        doNothing().when(accountService).withdrawMoney(ACCOUNT_ID, price);
        doNothing().when(bonusService).addBonus(CUSTOMER_ID, price, ShopType.SHOP);
        when(priceThresholdCache.getPriceByType(PriceType.SMALL_PURCHASE)).thenReturn(BigDecimal.TEN);

        shopService.shopPayment(CUSTOMER_ID, ACCOUNT_ID, price);

        verify(accountService, times(1)).withdrawMoney(ACCOUNT_ID, price);
        verify(bonusService, times(1)).addBonus(CUSTOMER_ID, price, ShopType.SHOP);

        assertEquals(1, shopService.getReturnCounter());
    }

    @Test
    void onlinePayment_returnIsNotIncrementedWhenPriceIsBigEnough() {
        BigDecimal price = BigDecimal.TEN;

        doNothing().when(accountService).withdrawMoney(ACCOUNT_ID, price);
        doNothing().when(bonusService).addBonus(CUSTOMER_ID, price, ShopType.ONLINE);
        when(priceThresholdCache.getPriceByType(PriceType.SMALL_PURCHASE)).thenReturn(BigDecimal.ONE);

        shopService.onlinePayment(CUSTOMER_ID, ACCOUNT_ID, price);

        verify(accountService, times(1)).withdrawMoney(ACCOUNT_ID, price);
        verify(bonusService, times(1)).addBonus(CUSTOMER_ID, price, ShopType.ONLINE);

        assertEquals(0, shopService.getReturnCounter());
    }

    @Test
    void onlinePayment_returnIsIncrementedWhenPriceIsNotBigEnough() {
        BigDecimal price = BigDecimal.ONE;

        doNothing().when(accountService).withdrawMoney(ACCOUNT_ID, price);
        doNothing().when(bonusService).addBonus(CUSTOMER_ID, price, ShopType.ONLINE);
        when(priceThresholdCache.getPriceByType(PriceType.SMALL_PURCHASE)).thenReturn(BigDecimal.TEN);

        shopService.onlinePayment(CUSTOMER_ID, ACCOUNT_ID, price);

        verify(accountService, times(1)).withdrawMoney(ACCOUNT_ID, price);
        verify(bonusService, times(1)).addBonus(CUSTOMER_ID, price, ShopType.ONLINE);

        assertEquals(1, shopService.getReturnCounter());
    }

    @Test
    void getBonuses_shouldJustReturnAmountOfBonusesForCustomer() {
        BigDecimal expected = BigDecimal.ONE;
        when(bonusService.getBonusAmount(CUSTOMER_ID)).thenReturn(expected);

        BigDecimal result = shopService.getBonuses(CUSTOMER_ID);
        assertEquals(expected, result);
    }

    @Test
    void getMoney_shouldJustReturnAmountOfMoneyForAccount() {
        BigDecimal expected = BigDecimal.ONE;
        when(accountService.getMoney(ACCOUNT_ID)).thenReturn(expected);

        BigDecimal result = shopService.getMoney(ACCOUNT_ID);
        assertEquals(expected, result);
    }

    @Test
    void getReturnCounter() {
        // более осознано тестируется в ходе тестирования методов покупок
        assertEquals(0, shopService.getReturnCounter());
    }
}