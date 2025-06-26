package com.pixel.demo.security;

import com.pixel.demo.model.User;
import com.pixel.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if (username.contains("@")) {
            Optional<User> userByEmail = userRepository.findByEmail(username);
            if (userByEmail.isPresent()) {
                return new CustomUserDetails(userByEmail.get(), username);
            }
        } else {
            Optional<User> userByPhone = userRepository.findByPhone(username);
            if (userByPhone.isPresent()) {
                return new CustomUserDetails(userByPhone.get(), username);
            }
        }
        throw new UsernameNotFoundException("Пользователь с email или телефоном " + username + " не найден");
    }
}