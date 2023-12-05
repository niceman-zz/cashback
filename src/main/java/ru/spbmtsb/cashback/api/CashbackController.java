package ru.spbmtsb.cashback.api;

import org.springframework.web.bind.annotation.*;
import ru.spbmtsb.cashback.service.ShopService;

import java.math.BigDecimal;

@RestController
@RequestMapping("api")
public class CashbackController {
    private static final long ACCOUNT_ID = 1L;
    private static final long USER_ID = 1L;

    private final ShopService shopService;

    public CashbackController(ShopService shopService) {
        this.shopService = shopService;
    }

    @PostMapping("/payment/Shop/{amount}")
    public void shopPayment(@PathVariable BigDecimal amount) {
        // в реальном приложении, я так понимаю, у нас был бы id счёта и, наверное, id пользователя
        // у нас в требованиях такого нет, но раз уж добавили слой БД, то будем работать с ними
        shopService.shopPayment(USER_ID, ACCOUNT_ID, amount);
    }

    @PostMapping("/payment/Online/{amount}")
    public void onlinePayment(@PathVariable BigDecimal amount) {
        // в реальном приложении, я так понимаю, у нас был бы id счёта и, наверное, id пользователя
        // у нас в требованиях такого нет, но раз уж добавили слой БД, то будем работать с ними
        shopService.onlinePayment(USER_ID, ACCOUNT_ID, amount);
    }

    @GetMapping("bankAccountOfEMoney")
    public BigDecimal getBonuses() {
        // в реальном приложении, я так понимаю, у нас был бы id счёта и, наверное, id пользователя
        // у нас в требованиях такого нет, но раз уж добавили слой БД, то будем работать с ними
        return shopService.getBonuses(USER_ID);
    }

    @GetMapping("money")
    public BigDecimal getMoney() {
        // в реальном приложении, я так понимаю, у нас был бы id счёта и, наверное, id пользователя
        // у нас в требованиях такого нет, но раз уж добавили слой БД, то будем работать с ними
        return shopService.getMoney(ACCOUNT_ID);
    }

    @GetMapping("returns")
    public int getReturns() {
        return shopService.getReturnCounter();
    }
}
