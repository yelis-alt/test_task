package com.example.test_task.chron;

import com.example.test_task.persistence.repo.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class BalanceScheduledTask {
    private final static BigDecimal maxCoefficient = new BigDecimal("2.07");
    private final static BigDecimal addCoefficient = new BigDecimal("1.10");

    private final AccountRepository accountRepository;

    @Scheduled(fixedRate = 30, initialDelay = 1, timeUnit = TimeUnit.SECONDS)
    public void increaseBalances() {
        accountRepository
                .findAll()
                .forEach(account -> {
                    log.debug("Trying to increase account for userId: {} by {}%",
                            account.getUserId(),
                            coefficientToPercentage(addCoefficient));
                    account.setBalance(account.getBalance().multiply(addCoefficient));
                    BigDecimal limit = account.getInitialBalance().multiply(maxCoefficient);

                    if (account.getBalance().compareTo(limit) < 0) {
                        accountRepository.save(account);
                        log.info("Successfully increased account for userId: {} by {}%",
                                account.getUserId(),
                                coefficientToPercentage(addCoefficient));
                    } else {
                        log.warn("Failed to increase account for userId: {} by {}% due to limit of {}%",
                                account.getUserId(),
                                coefficientToPercentage(addCoefficient),
                                limitToPercentage(maxCoefficient));
                    }
                });
    }

    private BigDecimal coefficientToPercentage(BigDecimal val) {
        return val.subtract(BigDecimal.ONE).multiply(new BigDecimal("100"));
    }

    private BigDecimal limitToPercentage(BigDecimal val) {
        return val.multiply(new BigDecimal("100"));
    }
}
