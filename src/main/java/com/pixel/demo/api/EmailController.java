package com.pixel.demo.api;

import com.pixel.demo.dto.EmaiReqlDto;
import com.pixel.demo.dto.EmaiResDto;
import com.pixel.demo.model.User;
import com.pixel.demo.security.CustomUserDetails;
import com.pixel.demo.service.EmailService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
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
@RequestMapping("/email")
@Validated
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/{newEmail}")
    public ResponseEntity<EmaiResDto> createEmail(@PathVariable @Email(message = "email must be correct") String newEmail,
                                                  @AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = userDetails.getUser();
        EmaiResDto emaiResDto = emailService.addEmail(newEmail, user);
        return ResponseEntity.ok(emaiResDto);
    }

    @PutMapping
    public ResponseEntity<EmaiResDto> updateEmail(@RequestBody @Valid EmaiReqlDto emailDto) {

        EmaiResDto emaiResDto = emailService.updateEmail(emailDto);
        return ResponseEntity.ok(emaiResDto);
    }

    @DeleteMapping("/{email}")
    public void deleteEmail(@PathVariable @Email(message = "email must be correct") String email,
                            @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long id = userDetails.getUser().getId();
        emailService.deleteEmail(email, id);
    }
}
