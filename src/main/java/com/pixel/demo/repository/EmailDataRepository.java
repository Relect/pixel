package com.pixel.demo.repository;

import com.pixel.demo.model.EmailData;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.List;
import java.util.Optional;

public interface EmailDataRepository extends JpaRepository<EmailData, Long> {

    Optional<EmailData> findEmailDataByEmail(String email);
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<EmailData> findEmailDataByUserId(Long userId);
}
