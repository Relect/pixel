package com.pixel.demo.service;

import com.pixel.demo.dto.PhoneReqDto;
import com.pixel.demo.dto.PhoneResDto;
import com.pixel.demo.exception.LastPhoneDeletionException;
import com.pixel.demo.mapper.PhoneMapper;
import com.pixel.demo.model.PhoneData;
import com.pixel.demo.model.User;
import com.pixel.demo.repository.PhoneDataRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PhoneService {

    private final PhoneDataRepository phoneDataRepository;
    private final PhoneMapper phoneMapper;

    @Transactional
    public PhoneResDto addPhone(String phone, User user) {

        PhoneData phoneData = new PhoneData();
        phoneData.setUser(user);
        phoneData.setPhone(phone);
        phoneData = phoneDataRepository.save(phoneData);
        return phoneMapper.toDto(phoneData);
    }

    @Transactional
    public PhoneResDto updatePhone(PhoneReqDto phoneDto) {

        PhoneData phoneData = phoneDataRepository.findPhoneDataByPhone(phoneDto.oldPhone())
                .orElseThrow(() -> new EntityNotFoundException("Phone:" + phoneDto.oldPhone() + " not found."));

        phoneData.setPhone(phoneData.getPhone());
        phoneData = phoneDataRepository.save(phoneData);
        return phoneMapper.toDto(phoneData);
    }

    @Transactional
    public void deletePhone(String phone, Long userId) {

        List<PhoneData> phoneDataList = phoneDataRepository.findPhoneDataByUserId(userId);
        PhoneData phoneToDelete = phoneDataList.stream()
                .filter(phoneData -> phoneData.getPhone().equals(phone))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Phone:" + phone + " not found."));

        if (phoneDataList.size() == 1) {
            throw new LastPhoneDeletionException(phone);
        }
        phoneDataRepository.delete(phoneToDelete);
    }
}
