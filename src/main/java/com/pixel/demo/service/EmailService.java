package com.pixel.demo.service;

import com.pixel.demo.dto.RequestEmailDto;
import com.pixel.demo.exception.LastEmailDeletionException;
import com.pixel.demo.model.EmailData;
import com.pixel.demo.repository.EmailDataRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final EmailDataRepository emailDataRepository;

    @Transactional
    public EmailData addEmail(String email, Long userId) {
        return null;
    }

    @Transactional
    public EmailData updateEmail(RequestEmailDto emailDto, Long userId) {
        
    }

    @Transactional
    public void deleteEmail(String email, Long userId) {

        List<EmailData> emailDataList = emailDataRepository.findEmailDataByUserId(userId);
        EmailData emailToDelete = emailDataList.stream()
                .filter(emailData -> emailData.getEmail().equals(email))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Email:" + email + " not found."));

        try {
            Thread.sleep(5_000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (emailDataList.size() == 1) {
            throw new LastEmailDeletionException(email);
        }
        emailDataRepository.delete(emailToDelete);
    }
}
