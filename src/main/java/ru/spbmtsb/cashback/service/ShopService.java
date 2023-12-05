package ru.spbmtsb.cashback.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spbmtsb.cashback.constants.CashbackType;
import ru.spbmtsb.cashback.constants.PriceType;
import ru.spbmtsb.cashback.constants.ShopType;
import ru.spbmtsb.cashback.service.cache.PriceThresholdCache;

import java.math.BigDecimal;

@Service
public class ShopService {
    private final AccountService accountService;
    private final BonusService bonusService;
    private final PriceThresholdCache priceThresholdCache;

    private int returnCounter = 0;

    public ShopService(AccountService accountService, BonusService bonusService, PriceThresholdCache priceThresholdCache) {
        this.accountService = accountService;
        this.bonusService = bonusService;
        this.priceThresholdCache = priceThresholdCache;
    }

    @Transactional
    public void shopPayment(long customerId, long accountId, BigDecimal price) {
        processPayment(customerId, accountId, price, ShopType.SHOP);
    }

    @Transactional
    public void onlinePayment(long customerId, long accountId, BigDecimal price) {
        processPayment(customerId, accountId, price, ShopType.ONLINE);
    }

    private void processPayment(long customerId, long accountId, BigDecimal price, ShopType shopType) {
        accountService.withdrawMoney(accountId, price);
        addBonus(customerId, price, shopType);
        processReturn(price);
    }

    private void addBonus(long customerId, BigDecimal price, ShopType shopType) {
        bonusService.addBonus(customerId, price, shopType);
    }

    // что бы это ни значило
    private void processReturn(BigDecimal price) {
        if (price.compareTo(priceThresholdCache.getPriceByType(PriceType.SMALL_PURCHASE)) < 0) {
            returnCounter++;
        }
    }

    public BigDecimal getBonuses(long customerId) {
        return bonusService.getBonusAmount(customerId);
    }

    public BigDecimal getMoney(long accountId) {
        return accountService.getMoney(accountId);
    }

    public int getReturnCounter() {
        return returnCounter;
    }
}
