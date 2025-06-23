package com.pixel.demo.repository;

import com.pixel.demo.model.PhoneData;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PhoneDataRepository extends JpaRepository<PhoneData, Long> {
    Optional<PhoneData> findPhoneDataByPhone(String phone);
    @Query("SELECT p FROM PhoneData p WHERE p.user.id = :userId")
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<PhoneData> findPhoneDataByUserId(@Param("userId") Long userId);
}
