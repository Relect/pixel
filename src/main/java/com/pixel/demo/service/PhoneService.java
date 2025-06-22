package com.pixel.demo.service;

import com.pixel.demo.exception.LastPhoneDeletionException;
import com.pixel.demo.model.PhoneData;
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

    @Transactional
    public void deletePhone(String phone) {
        PhoneData phoneToDelete = phoneDataRepository.findPhoneDataByPhone(phone)
                .orElseThrow(() -> new EntityNotFoundException("Phone:" + phone + " not found."));

        List<PhoneData> phoneDataList = phoneDataRepository.findPhoneDataByUserId(phoneToDelete.getUser().getId());
        if (phoneDataList.size() == 1) {
            throw new LastPhoneDeletionException(phone);
        }
        phoneDataRepository.delete(phoneToDelete);
    }
}
