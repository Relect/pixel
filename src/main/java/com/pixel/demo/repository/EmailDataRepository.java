package com.pixel.demo.repository;

import com.pixel.demo.model.EmailData;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EmailDataRepository extends JpaRepository<EmailData, Long> {

    @Query("SELECT e FROM EmailData e WHERE e.email = :email")
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<EmailData> findEmailDataByEmail(@Param("email") String email);

    @Query("SELECT e FROM EmailData e WHERE e.user.id = :userId")
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<EmailData> findEmailDataByUserId(@Param("userId") Long userId);
}
