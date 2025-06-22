package com.pixel.demo.service;

import com.pixel.demo.exception.LastEmailDeletionException;
import com.pixel.demo.exception.LastPhoneDeletionException;
import com.pixel.demo.model.EmailData;
import com.pixel.demo.model.PhoneData;
import com.pixel.demo.repository.EmailDataRepository;
import com.pixel.demo.repository.PhoneDataRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final EmailDataRepository emailDataRepository;
    private final PhoneDataRepository phoneDataRepository;

    @Transactional
    public void deletePhone(String phone) {
        PhoneData phoneToDelete = phoneDataRepository.findPhoneDataByPhone(phone)
                .orElseThrow(() -> new EntityNotFoundException("Phone:" + phone + " not found."));

        long phoneCount = phoneDataRepository.countByUserId(phoneToDelete.getUser().getId());
        if (phoneCount == 1) {
            throw new LastPhoneDeletionException(phone);
        }
        phoneDataRepository.delete(phoneToDelete);
    }

    @Transactional
    public void deleteEmail(String email) {
        EmailData emailToDelete = emailDataRepository.findEmailDataByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Email:" + email + " not found."));

        long emailCount = emailDataRepository.countByUserId(emailToDelete.getUser().getId());
        if (emailCount == 1) {
            throw new LastEmailDeletionException(email);
        }
        emailDataRepository.delete(emailToDelete);
    }
}
