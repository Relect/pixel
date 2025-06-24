package com.pixel.demo.api;

import com.pixel.demo.dto.SearchUserReqDto;
import com.pixel.demo.dto.UserResDto;
import com.pixel.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResDto> searchUser(SearchUserReqDto searchUserReqDto)
}
