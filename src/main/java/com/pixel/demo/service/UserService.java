package com.pixel.demo.service;

import com.pixel.demo.dto.UserResDto;
import com.pixel.demo.mapper.UserMapper;
import com.pixel.demo.model.User;
import com.pixel.demo.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional(readOnly = true)
    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id:" + id + " not found."));
    }

    @Transactional(readOnly = true)
    public UserResDto findByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User with email:" + email + " not found."));
        return userMapper.toDto(user);
    }

    @Transactional(readOnly = true)
    public UserResDto findByPhone(String phone) {
        User user = userRepository.findByPhone(phone)
                .orElseThrow(() -> new EntityNotFoundException("User with phone:" + phone + " not found."));
        return userMapper.toDto(user);
    }

    @Transactional(readOnly = true)
    public List<UserResDto> findByNameAndDateOfBirth(String name,
                                                     LocalDate dateOfBirth,
                                                     Pageable pageable) {


    }

}
