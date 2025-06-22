package com.pixel.demo.repository;

import com.pixel.demo.model.EmailData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailDataRepository extends JpaRepository<EmailData, Long> {

    Optional<EmailData> findEmailDataByEmail(String email);
    long countByUserId(Long usesId);
}
