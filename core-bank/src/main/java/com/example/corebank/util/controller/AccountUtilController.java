package com.example.corebank.util.controller;

import com.example.corebank.entity.Account;
import com.example.corebank.service.AccountService;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/accountUtil")
public class AccountUtilController {
    @Autowired
    private AccountService ac;

    volatile BigDecimal totalMoney = BigDecimal.valueOf(30_000L);

    @PostMapping("/replenish")
    public void replenishBalance(@RequestBody Account account){
        ac.replenishBalance(account.getAccountId(), account.getMoney());
        synchronized (totalMoney) {
            totalMoney = totalMoney.add(account.getMoney());
        }
    }

    @GetMapping("/getTotalS")
    public String getTotalMoneyS(){
        return totalMoney.toString();
    }

    @GetMapping("/getTotalDB")
    public String getTotalMoneyDB(){
        return ac.getTotalMoneyDB();
    }
}
