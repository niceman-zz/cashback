package ru.spbmtsb.cashback.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spbmtsb.cashback.entity.Account;
import ru.spbmtsb.cashback.error.InsufficientFundsException;
import ru.spbmtsb.cashback.repo.AccountRepository;

import java.math.BigDecimal;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional
    public void withdrawMoney(Long accountId, BigDecimal toWithdraw) {
        Account account = accountRepository.findById(accountId).orElseThrow();
        if (!hasEnoughMoney(account, toWithdraw)) {
            throw new InsufficientFundsException();
        }
        account.setMoney(account.getMoney().subtract(toWithdraw));
    }

    private boolean hasEnoughMoney(Account account, BigDecimal neededAmount) {
        return account.getMoney().compareTo(neededAmount) >= 0;
    }

    public BigDecimal getMoney(long accountId) {
        return accountRepository.findById(accountId).orElseThrow().getMoney();
    }
}
