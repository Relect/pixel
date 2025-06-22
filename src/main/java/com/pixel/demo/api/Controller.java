package com.pixel.demo.api;

import com.pixel.demo.service.EmailService;
import com.pixel.demo.service.PhoneService;
import com.pixel.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class Controller {

    private final UserService userService;
    private final PhoneService phoneService;
    private final EmailService emailService;

    @PostMapping("/email/{newEmail}")
    public ResponseEntity<String > createEmail(@PathVariable String newEmail) {
        return ResponseEntity.ok("авторизация успешна через jwt");
    }

    @PutMapping("/email/{newEmail}")
    public ResponseEntity<String > updateEmail(@PathVariable String newEmail) {
        return ResponseEntity.ok("авторизация успешна через jwt");
    }

    @DeleteMapping("/email/{email}")
    public void deleteEmail(@PathVariable String email) {
        emailService.deleteEmail(email);
    }

    @PostMapping("/phone/{newPhone}")
    public ResponseEntity<String > createPhone(@PathVariable String newPhone) {
        return ResponseEntity.ok("авторизация успешна через jwt");
    }

    @PutMapping("/phone/{newPhone}")
    public ResponseEntity<String > updatePhone(@PathVariable String newPhone) {
        return ResponseEntity.ok("авторизация успешна через jwt");
    }

    @DeleteMapping("/phone/{phone}")
    public void deletePhone(@PathVariable String phone) {
        phoneService.deletePhone(phone);
    }
}
