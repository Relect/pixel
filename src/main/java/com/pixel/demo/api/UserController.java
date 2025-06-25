package com.pixel.demo.api;

import com.pixel.demo.dto.SearchUserDto;
import com.pixel.demo.dto.UserResDto;
import com.pixel.demo.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<Page<UserResDto>> searchUser(@RequestBody @Valid SearchUserDto searchUserDto) {

        if (searchUserDto.email() != null || searchUserDto.phone() != null) {
            var userResDtoOpt = findByPhoneEmail(searchUserDto);
            if (userResDtoOpt.isEmpty()) {
                return ResponseEntity.notFound().build();
            } else {
                Page<UserResDto> page1User =
                        new PageImpl<>(List.of(userResDtoOpt.get()), Pageable.ofSize(1), 1);
                return ResponseEntity.ok(page1User);
            }
        }

        Page<UserResDto> page = userService.findByNameAndDateOfBirth(searchUserDto);
        return ResponseEntity.ok(page);
    }

    private Optional<UserResDto> findByPhoneEmail(SearchUserDto searchUserDto) {

        final String email = searchUserDto.email();
        final String phone = searchUserDto.phone();

        return Optional.ofNullable(email)
                .map(userService::findByEmail)
                .or(() -> Optional.ofNullable(phone).map(userService::findByPhone))
                .filter(user -> phone == null || email == null ||
                        user.phoneDataList().stream()
                                .anyMatch(p -> p.phone().equals(phone)))
                .filter(user -> searchUserDto.dateOfBirth() == null ||
                        user.dateOfBirth().isAfter(searchUserDto.dateOfBirth()))
                .filter(user -> searchUserDto.name() == null ||
                        user.name().startsWith(searchUserDto.name()));
    }
}
