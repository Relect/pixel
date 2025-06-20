package com.pixel.demo.repository;

import com.pixel.demo.model.PhoneData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneDataRepository extends JpaRepository<PhoneData, Long> {
}
