package com.pixel.demo.api;

import com.pixel.demo.dto.PhoneReqDto;
import com.pixel.demo.dto.PhoneResDto;
import com.pixel.demo.model.User;
import com.pixel.demo.security.CustomUserDetails;
import com.pixel.demo.service.PhoneService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/phone")
@Validated
@RequiredArgsConstructor
public class PhoneController {

    private final PhoneService phoneService;

    @PostMapping("{newPhone}")
    public ResponseEntity<PhoneResDto> createPhone(
            @PathVariable
            @Pattern(
                    regexp = "^7\\d{10}$",
                    message = "Телефон должен быть в формате  79207865432")
            String newPhone,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        User user = userDetails.getUser();
        PhoneResDto phoneResDto = phoneService.addPhone(newPhone, user);
        return ResponseEntity.ok(phoneResDto);
    }

    @PutMapping()
    public ResponseEntity<PhoneResDto> updatePhone(@RequestBody @Valid PhoneReqDto phoneDto) {

        PhoneResDto phoneResDto = phoneService.updatePhone(phoneDto);
        return ResponseEntity.ok(phoneResDto);
    }

    @DeleteMapping("/{phone}")
    public void deletePhone(
            @PathVariable
            @Pattern(
                    regexp = "^7\\d{10}$",
                    message = "Телефон должен быть в формате  79207865432")
            String phone,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        Long id = userDetails.getUser().getId();
        phoneService.deletePhone(phone, id);
    }
}
