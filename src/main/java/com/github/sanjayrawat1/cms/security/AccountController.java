package com.github.sanjayrawat1.cms.security;

import com.github.sanjayrawat1.cms.security.service.UserService;
import com.github.sanjayrawat1.cms.security.service.dto.UserDto;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Sanjay Singh Rawat
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AccountController {

    private final UserService userService;

    @GetMapping("/v1/account")
    public ResponseEntity<UserDto> getAccount() {
        return userService
            .getCurrentUser()
            .map(userDto -> ResponseEntity.ok().body(userDto))
            .orElseThrow(() -> new RuntimeException("User could not be found"));
    }

    @GetMapping("/v1/account/authorities")
    public ResponseEntity<Set<String>> getAccountAuthorities() {
        return ResponseEntity.ok().body(userService.getCurrentUserAuthorities());
    }
}
