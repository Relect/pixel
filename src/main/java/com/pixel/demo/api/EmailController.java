package com.pixel.demo.api;

import com.pixel.demo.dto.EmaiReqlDto;
import com.pixel.demo.dto.EmaiReslDto;
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
    public ResponseEntity<EmaiReslDto> createEmail(@PathVariable @Email(message = "email must be correct") String newEmail,
                                                   @AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = userDetails.getUser();
        EmaiReslDto emaiReslDto = emailService.addEmail(newEmail, user);
        return ResponseEntity.ok(emaiReslDto);
    }

    @PutMapping()
    public ResponseEntity<EmaiReslDto> updateEmail(@RequestBody @Valid EmaiReqlDto emailDto) {

        EmaiReslDto emaiReslDto = emailService.updateEmail(emailDto);
        return ResponseEntity.ok(emaiReslDto);
    }

    @DeleteMapping("/{email}")
    public void deleteEmail(@PathVariable @Email(message = "email must be correct") String email,
                            @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long id = userDetails.getUser().getId();
        emailService.deleteEmail(email, id);
    }
}
