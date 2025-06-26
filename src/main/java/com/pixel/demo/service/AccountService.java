package com.pixel.demo.service;

import com.pixel.demo.dto.AccountResDto;
import com.pixel.demo.exception.NotEnoughMoney;
import com.pixel.demo.mapper.AccountMapper;
import com.pixel.demo.model.Account;
import com.pixel.demo.repository.AccountRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;


    private static final int BATCH_SIZE = 1_000;
    private static final BigDecimal PERCENTAGE = new BigDecimal("0.10");
    private static final BigDecimal MAX_MULTIPLIER = new BigDecimal("2.07");

    @Scheduled(fixedRate = 30_000)
    @Transactional
    public void increaseBalances() {
        int page = 0;
        Slice<Account> slice;

        do {
            slice = accountRepository.findAccountByBalance(
                    PageRequest.of(page, BATCH_SIZE));

            List<Account> list = slice.stream()
                    .peek(account -> {
                        BigDecimal maxAllowed = account.getStartBalance().multiply(MAX_MULTIPLIER);
                        BigDecimal newBalance = account.getBalance().multiply(PERCENTAGE).min(maxAllowed);
                        account.setBalance(newBalance);
                    })
                    .collect(Collectors.toList());

            accountRepository.saveAll(list);
            page++;
        } while (slice.hasNext());
    }

    @Transactional
    public AccountResDto transfer(Long from, Long to, BigDecimal summ) {

        Account accountFrom = accountRepository.findByIdWithPessimisticLock(from)
                .orElseThrow(() -> new EntityNotFoundException("Account with userId:" + from + " not found."));
        Account accountTo = accountRepository.findByIdWithPessimisticLock(to)
                .orElseThrow(() -> new EntityNotFoundException("Account with userId:" + to + " not found."));

        if (accountFrom.getBalance().compareTo(summ) < 0) {
            throw  new NotEnoughMoney("User with Id:" + accountFrom.getUser().getId() + "not enough money.");
        }
        accountFrom.getBalance().subtract(summ);
        accountTo.getBalance().add(summ);
        accountRepository.save(accountFrom);
        accountRepository.save(accountTo);
        return accountMapper.toDto(accountFrom);
    }
}
