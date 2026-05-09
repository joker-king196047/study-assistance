package com.study.controller;

import com.study.common.Result;
import com.study.dto.LoginRequest;
import com.study.dto.RegisterRequest;
import com.study.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/api/auth/register")
    public Result<Map<String, Object>> register(@Valid @RequestBody RegisterRequest request) {
        Map<String, Object> result = userService.register(
                request.getUsername(),
                request.getEmail(),
                request.getPassword()
        );
        return Result.success(result);
    }

    @PostMapping("/api/auth/login")
    public Result<Map<String, Object>> login(@Valid @RequestBody LoginRequest request) {
        Map<String, Object> result = userService.login(
                request.getUsername(),
                request.getPassword()
        );
        return Result.success(result);
    }

    @GetMapping("/api/auth/me")
    public Result<Map<String, Object>> getUserInfo(Authentication authentication) {
        String username = authentication.getName();
        Map<String, Object> userInfo = userService.getUserInfo(username);
        return Result.success(userInfo);
    }

    @PostMapping("/api/auth/logout")
    public Result<Void> logout() {
        return Result.success();
    }
}
