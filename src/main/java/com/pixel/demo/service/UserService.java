package com.pixel.demo.service;

import com.pixel.demo.dto.SearchUserDto;
import com.pixel.demo.dto.UserResDto;
import com.pixel.demo.mapper.UserMapper;
import com.pixel.demo.model.User;
import com.pixel.demo.repository.UserRepository;
import com.pixel.demo.repository.UserSpecifications;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional(readOnly = true)
    @Cacheable(value = "user", key = "#id")
    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id:" + id + " not found."));
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "userCache", key = "#email")
    public UserResDto findByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User with email:" + email + " not found."));
        return userMapper.toDto(user);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "userCache", key = "#phone")
    public UserResDto findByPhone(String phone) {
        User user = userRepository.findByPhone(phone)
                .orElseThrow(() -> new EntityNotFoundException("User with phone:" + phone + " not found."));
        return userMapper.toDto(user);
    }

    @Transactional(readOnly = true)
    public Page<UserResDto> findByNameAndDateOfBirth(SearchUserDto searchUserDto) {

        return UserSpecifications.findUsersWithDateOfBirthAndName(searchUserDto, userRepository)
                .map(userMapper::toDto);
    }

}
