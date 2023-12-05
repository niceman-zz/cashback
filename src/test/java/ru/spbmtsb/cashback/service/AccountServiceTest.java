package ru.spbmtsb.cashback.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.spbmtsb.cashback.entity.Account;
import ru.spbmtsb.cashback.error.InsufficientFundsException;
import ru.spbmtsb.cashback.repo.AccountRepository;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {
    private static final long ACCOUNT_ID = 1L;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    @Test
    void withdrawMoney_incorrectAccountThrowsException() {
        when(accountRepository.findById(ACCOUNT_ID)).thenReturn(Optional.empty());

        Assertions.assertThrows(NoSuchElementException.class, () -> accountService.withdrawMoney(ACCOUNT_ID, BigDecimal.ZERO));
    }

    @Test
    void withdrawMoney_notEnoughMoneyThrowsException() {
        Account account = new Account();
        account.setMoney(BigDecimal.ZERO);

        when(accountRepository.findById(ACCOUNT_ID)).thenReturn(Optional.of(account));

        Assertions.assertThrows(InsufficientFundsException.class, () -> accountService.withdrawMoney(ACCOUNT_ID, BigDecimal.ONE));
    }

    @Test
    void withdrawMoney() {
        Account account = new Account();
        account.setMoney(BigDecimal.TEN);

        when(accountRepository.findById(ACCOUNT_ID)).thenReturn(Optional.of(account));

        accountService.withdrawMoney(ACCOUNT_ID, BigDecimal.ONE);

        assertEquals(BigDecimal.valueOf(9), account.getMoney());
    }

    @Test
    void getMoney_incorrectAccountThrowsException() {
        when(accountRepository.findById(ACCOUNT_ID)).thenReturn(Optional.empty());

        Assertions.assertThrows(NoSuchElementException.class, () -> accountService.getMoney(ACCOUNT_ID));
    }

    @Test
    void getMoney() {
        Account account = new Account();
        account.setMoney(BigDecimal.ONE);
        when(accountRepository.findById(ACCOUNT_ID)).thenReturn(Optional.of(account));

        BigDecimal result = accountService.getMoney(ACCOUNT_ID);
        assertEquals(result, account.getMoney());
    }
}