package com.pixel.demo.repository;

import com.pixel.demo.model.Account;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT a FROM Account a WHERE a.user = :id")
    Optional<Account> findByIdWithPessimisticLock(@Param("id") Long id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT a FROM Account a WHERE a.balance <= (a.startBalance * :maxPercent) " +
            "ORDER BY a.id ASC")
    Slice<Account> findAccountByBalanceWithLock(@Param("maxPercent") double maxPercent,
                                                Pageable pageable);

    default Slice<Account> findAccountByBalance(Pageable pageable) {
        return findAccountByBalanceWithLock(2.07, pageable);
    }
}
