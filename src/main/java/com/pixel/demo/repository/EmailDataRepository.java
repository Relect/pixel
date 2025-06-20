package com.pixel.demo.repository;

import com.pixel.demo.model.EmailData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailDataRepository extends JpaRepository<EmailData, Long> {
}
