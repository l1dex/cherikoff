package com.example.corebank.service;


import com.example.corebank.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class ScheduledRepelishService {
    private final ConcurrentLinkedQueue<Account> buffer = new ConcurrentLinkedQueue<>();

    @Autowired
    private AccountService accountService;

    public void addRepelish(String id, BigDecimal money){
        Account account = new Account();
        account.setAccountId(id);
        account.setMoney(money);

        buffer.add(account);
    }

    @Scheduled(cron = "*/10 * * * * *")
    private void repelish(){
        System.out.println("scheduled relelish count - " + buffer.size());
        Account poll = buffer.poll();
        if(poll != null){
            accountService.replenishBalance(poll.getAccountId(), poll.getMoney());
        }
    }
}
