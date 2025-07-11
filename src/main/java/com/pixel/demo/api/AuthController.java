package com.pixel.demo.api;

import com.pixel.demo.dto.AuthReqRes;
import com.pixel.demo.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<AuthReqRes> signIn(@RequestBody AuthReqRes signInRequest){
        return ResponseEntity.ok(authService.signIn(signInRequest));
    }
}
