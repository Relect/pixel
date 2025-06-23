package com.pixel.demo.api;

import com.pixel.demo.dto.RequestEmailDto;
import com.pixel.demo.security.CustomUserDetails;
import com.pixel.demo.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/{newEmail}")
    public ResponseEntity<String > createEmail(@PathVariable String newEmail,
                                               @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long id = userDetails.getUser().getId();
        return ResponseEntity.ok("авторизация успешна через jwt");
    }

    @PutMapping()
    public ResponseEntity<String > updateEmail(@RequestBody RequestEmailDto emailDto,
                                               @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long id = userDetails.getUser().getId();
        return ResponseEntity.ok("авторизация успешна через jwt");
    }

    @DeleteMapping("/{email}")
    public void deleteEmail(@PathVariable String email,
                            @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long id = userDetails.getUser().getId();
        emailService.deleteEmail(email, id);
    }
}
