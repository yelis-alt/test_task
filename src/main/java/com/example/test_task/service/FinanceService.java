package com.example.test_task.service;

import com.example.test_task.api.dto.business.AccountDto;
import com.example.test_task.api.dto.request.TransferRequest;
import com.example.test_task.exception.InsufficientFundsException;
import com.example.test_task.persistence.entity.AccountEntity;
import com.example.test_task.persistence.mapper.AccountMapper;
import com.example.test_task.persistence.repo.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
@Service
@RequiredArgsConstructor
public class FinanceService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    private final ReentrantLock lock = new ReentrantLock();

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public AccountDto transfer(TransferRequest request, Long fromUserId) {
        log.debug("Transferring money from {} to {}", fromUserId, request.getToUserId());

        lock.lock();
        try {
            AccountEntity fromAccount = accountRepository.findByUserId(fromUserId)
                    .orElseThrow(() -> throwNotFoundException(fromUserId));
            AccountEntity toAccount = accountRepository.findByUserId(request.getToUserId())
                    .orElseThrow(() -> throwNotFoundException(request.getToUserId()));

            BigDecimal amountToTransfer = BigDecimal.valueOf(request.getValue());
            if (fromAccount.getBalance().compareTo(amountToTransfer) < 0) {
                log.error("Not enough money to transfer {} from user_id: {}",
                        amountToTransfer,
                        fromUserId);
                throw new InsufficientFundsException("Insufficient funds");
            }

            fromAccount.setBalance(fromAccount.getBalance().subtract(amountToTransfer));
            toAccount.setBalance(toAccount.getBalance().add(amountToTransfer));

            accountRepository.save(fromAccount);
            accountRepository.save(toAccount);

            return accountMapper.toDto(fromAccount);
        } finally {
            lock.unlock();
        }
    }

    private EntityNotFoundException throwNotFoundException(Long userId) {
        throw new EntityNotFoundException(String.format("Account not found for user: %s", userId));
    }
}
