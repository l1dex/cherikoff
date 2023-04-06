package com.example.corebank.util.init;

import com.example.corebank.entity.Account;
import com.example.corebank.repository.AccountRepository;
import com.example.corebank.service.AccountService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class AccountInitComponent {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    @PostConstruct
    void init() {
        var entity1 = new Account();
        var entity2 = new Account();
        entity1.setAccountId("1111 1111 1111 1111");
        entity1.setMoney(BigDecimal.valueOf(10_000L));
        entity2.setAccountId("2222 2222 2222 2222");
        entity2.setMoney(BigDecimal.valueOf(20_000L));
        accountRepository.save(entity1);
        accountRepository.save(entity2);
        accountService.totalMoney = BigDecimal.valueOf(30_000L);
    }
}
