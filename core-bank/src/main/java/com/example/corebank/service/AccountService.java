package com.example.corebank.service;

import com.example.corebank.entity.Account;
import com.example.corebank.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Slf4j
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ScheduledRepelishService scheduledRepelishService;

    @Retryable(retryFor = CannotAcquireLockException.class, recover = "recover")
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void replenishBalance(String accountId, BigDecimal sum){
        Account account1 = accountRepository.findById(accountId).get();
        account1.setMoney(account1.getMoney().add(sum));

        accountRepository.save(account1);
    }

    @Recover
    public void recover(CannotAcquireLockException exception, String accountId, BigDecimal sum) {
        scheduledRepelishService.addRepelish(accountId, sum);
    }

    public String getTotalMoneyDB(){
        return accountRepository.findAll().stream()
                .map(Account::getMoney)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .toString();
    }
}
