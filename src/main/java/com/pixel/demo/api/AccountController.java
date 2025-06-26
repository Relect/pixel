package com.pixel.demo.api;

import com.pixel.demo.dto.AccountReqDto;
import com.pixel.demo.dto.AccountResDto;
import com.pixel.demo.security.CustomUserDetails;
import com.pixel.demo.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;


    @PostMapping
    public ResponseEntity<AccountResDto> transfer(@RequestBody @Valid AccountReqDto accountReqDto,
                                                  @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long from = userDetails.getUser().getId();
        AccountResDto dto = accountService.transfer(from, accountReqDto.userId(), accountReqDto.balance());
        return ResponseEntity.ok(dto);
    }
}
