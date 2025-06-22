package com.pixel.demo.repository;

import com.pixel.demo.model.PhoneData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PhoneDataRepository extends JpaRepository<PhoneData, Long> {
    Optional<PhoneData> findPhoneDataByPhone(String phone);
    long countByUserId(Long usesId);
}
