package com.pixel.demo.api;

import com.pixel.demo.dto.AuthReqRes;
import com.pixel.demo.service.AuthService;
import com.pixel.demo.service.RedisBlacklistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final RedisBlacklistService blacklistService;

    @PostMapping("/signin")
    public ResponseEntity<AuthReqRes> signIn(@RequestBody AuthReqRes signInRequest){
        return ResponseEntity.ok(authService.signIn(signInRequest));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(
            @RequestHeader("Authorization") String authHeader) {

        String token = authHeader.substring(7);
        //long expirationTime = jwtTokenProvider.getRemainingExpirationTime(token); // Оставшееся время жизни

        //todo blacklistService.addToBlacklist(token, expirationTime);

        return ResponseEntity.ok("Logged out successfully");
    }
}
