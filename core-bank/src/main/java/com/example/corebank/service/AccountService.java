package com.example.corebank.service;

import com.example.corebank.entity.Account;
import com.example.corebank.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    public BigDecimal totalMoney;

    public void replenishBalance(String accountId, BigDecimal sum){
        Account account1 = accountRepository.findById(accountId).get();
        account1.setMoney(account1.getMoney().add(sum));
        synchronized (totalMoney) {
            totalMoney = totalMoney.add(sum);
        }

        accountRepository.save(account1);
    }

    public String getTotalMoneyS(){
        return totalMoney.toString();
    }

    public String getTotalMoneyDB(){
        return accountRepository.findAll().stream()
                .map(Account::getMoney)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .toString();
    }
}
