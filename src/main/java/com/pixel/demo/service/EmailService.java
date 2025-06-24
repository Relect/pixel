package com.pixel.demo.service;

import com.pixel.demo.dto.EmaiReqlDto;
import com.pixel.demo.dto.EmaiResDto;
import com.pixel.demo.exception.LastEmailDeletionException;
import com.pixel.demo.mapper.EmailMapper;
import com.pixel.demo.model.EmailData;
import com.pixel.demo.model.User;
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
    private final EmailMapper emailMapper;

    @Transactional
    public EmaiResDto addEmail(String email, User user) {

        EmailData emailData = new EmailData();
        emailData.setUser(user);
        emailData.setEmail(email);
        emailData = emailDataRepository.save(emailData);
        return emailMapper.toDto(emailData);
    }

    @Transactional
    public EmaiResDto updateEmail(EmaiReqlDto emailDto) {

        EmailData emailData = emailDataRepository.findEmailDataByEmail(emailDto.oldEmail())
                .orElseThrow(() -> new EntityNotFoundException("Email:" + emailDto.oldEmail() + " not found."));

        emailData.setEmail(emailDto.email());
        emailData = emailDataRepository.save(emailData);
        return emailMapper.toDto(emailData);
    }

    @Transactional
    public void deleteEmail(String email, Long userId) {

        List<EmailData> emailDataList = emailDataRepository.findEmailDataByUserId(userId);
        EmailData emailToDelete = emailDataList.stream()
                .filter(emailData -> emailData.getEmail().equals(email))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Email:" + email + " not found."));

        if (emailDataList.size() == 1) {
            throw new LastEmailDeletionException(email);
        }
        emailDataRepository.delete(emailToDelete);
    }
}
