package com.pixel.demo.service;

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
    public void deleteEmail(String email) {
        EmailData emailToDelete = emailDataRepository.findEmailDataByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Email:" + email + " not found."));

        List<EmailData> emailDataList = emailDataRepository.findEmailDataByUserId(emailToDelete.getUser().getId());
        if (emailDataList.size() == 1) {
            throw new LastEmailDeletionException(email);
        }

        try {
            Thread.sleep(5_000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        emailDataRepository.delete(emailToDelete);
    }
}
