package com.study.controller;

import com.study.common.Result;
import com.study.dto.ProfileRequest;
import com.study.entity.UserProfile;
import com.study.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/check-first")
    public Result<Map<String, Object>> checkFirstLearning(Authentication authentication) {
        String username = authentication.getName();
        Map<String, Object> result = profileService.checkFirstLearning(username);
        return Result.success(result);
    }

    @GetMapping("/global")
    public Result<UserProfile> getGlobalProfile(Authentication authentication) {
        String username = authentication.getName();
        UserProfile profile = profileService.getGlobalProfile(username);
        return Result.success(profile);
    }

    @PostMapping("/global")
    public Result<Void> saveGlobalProfile(
            Authentication authentication,
            @RequestBody ProfileRequest request) {
        String username = authentication.getName();
        profileService.saveGlobalProfile(username, request);
        return Result.success();
    }

    @GetMapping("/directions")
    public Result<List<Map<String, Object>>> getLearningDirections() {
        List<Map<String, Object>> directions = profileService.getLearningDirections();
        return Result.success(directions);
    }

    @GetMapping("/local/{directionId}")
    public Result<Map<String, Object>> getLocalProfile(
            Authentication authentication,
            @PathVariable String directionId) {
        String username = authentication.getName();
        Map<String, Object> profile = profileService.getLocalProfile(username, directionId);
        return Result.success(profile);
    }

    @PostMapping("/local/{directionId}")
    public Result<Void> saveLocalProfile(
            Authentication authentication,
            @PathVariable String directionId,
            @RequestBody Map<String, Object> profileData) {
        String username = authentication.getName();
        profileService.saveLocalProfile(username, directionId, profileData);
        return Result.success();
    }

    @PostMapping("/complete-setup")
    public Result<Void> completeProfileSetup(
            Authentication authentication,
            @RequestBody Map<String, String> request) {
        String username = authentication.getName();
        String directionId = request.get("directionId");
        profileService.completeProfileSetup(username, directionId);
        return Result.success();
    }
}