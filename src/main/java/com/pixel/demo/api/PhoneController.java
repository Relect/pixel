package com.pixel.demo.api;

import com.pixel.demo.service.PhoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/phone")
@RequiredArgsConstructor
public class PhoneController {

    private final PhoneService phoneService;

    @PostMapping("{newPhone}")
    public ResponseEntity<String > createPhone(@PathVariable String newPhone) {
        return ResponseEntity.ok("авторизация успешна через jwt");
    }

    @PutMapping("/{newPhone}")
    public ResponseEntity<String > updatePhone(@PathVariable String newPhone) {
        return ResponseEntity.ok("авторизация успешна через jwt");
    }

    @DeleteMapping("/{phone}")
    public void deletePhone(@PathVariable String phone) {
        phoneService.deletePhone(phone);
    }
}
