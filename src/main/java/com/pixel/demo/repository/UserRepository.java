package com.pixel.demo.repository;

import com.pixel.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, QuerydslPredicateExecutor<User> {

    @Query("SELECT e.user FROM EmailData e WHERE e.email = :email")
    Optional<User> findByEmail(@Param("email") String email);

    @Query("SELECT p.user FROM PhoneData p WHERE p.phone = :phone")
    Optional<User> findByPhone(@Param("phone") String phone);
}
