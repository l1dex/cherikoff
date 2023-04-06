package com.example.corebank.service;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Service
public class AsyncRepelishService {
    private ExecutorService executorService;

    @PostConstruct
    public void init() {
        executorService = Executors.newFixedThreadPool(5);
    }

    public void repelish(String id, BigDecimal sum, BiConsumer<String, BigDecimal> fun){
        executorService.submit(() -> fun.accept(id,sum));
    }
}
