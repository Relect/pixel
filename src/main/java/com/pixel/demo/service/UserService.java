package com.pixel.demo.service;

import com.pixel.demo.model.EmailData;
import com.pixel.demo.model.PhoneData;
import com.pixel.demo.model.User;
import com.pixel.demo.repository.EmailDataRepository;
import com.pixel.demo.repository.PhoneDataRepository;
import com.pixel.demo.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final EmailDataRepository emailDataRepository;
    private final PhoneDataRepository phoneDataRepository;

    @Transactional
    public void deletePhone(String phone) {
        PhoneData phoneToDelete = phoneDataRepository.findPhoneDataByPhone(phone)
                .orElseThrow(() -> new EntityNotFoundException("Phone:" + phone + " not found."));

        User user = phoneToDelete.getUser();
        user.getPhoneData().remove(phoneToDelete);
        userRepository.save(user);
    }

    @Transactional
    public void deleteEmail(String email) {
        EmailData emailToDelete = emailDataRepository.findEmailDataByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Email:" + email + " not found."));

        User user = emailToDelete.getUser();
        user.getEmailData().remove(emailToDelete);

        try {
            Thread.sleep(5_000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        userRepository.save(user);
    }
}
