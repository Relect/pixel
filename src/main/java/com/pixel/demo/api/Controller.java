package com.pixel.demo.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class Controller {

    AuthenticationManager authenticationManager;

    @PostMapping("/email/{newEmail}")
    public ResponseEntity<String > createEmail(@PathVariable String newEmail) {
        return ResponseEntity.ok("авторизация успешна через jwt");
    }
}
