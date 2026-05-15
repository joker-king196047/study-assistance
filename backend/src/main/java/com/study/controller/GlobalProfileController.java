package com.study.controller;

import com.study.common.Result;
import com.study.entity.GlobalProfile;
import com.study.service.GlobalProfileService;
import com.study.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/global-profile")
public class GlobalProfileController {

    @Autowired
    private GlobalProfileService globalProfileService;

    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping("/check")
    public Result checkProfile(@RequestHeader("Authorization") String token) {
        try {
            String jwt = token.replace("Bearer ", "");
            Long userId = jwtUtils.getUserIdFromToken(jwt);

            boolean isCompleted = globalProfileService.isProfileCompleted(userId);
            return Result.success(isCompleted);
        } catch (Exception e) {
            return Result.error("检查画像状态失败");
        }
    }

    @GetMapping
    public Result getProfile(@RequestHeader("Authorization") String token) {
        try {
            String jwt = token.replace("Bearer ", "");
            Long userId = jwtUtils.getUserIdFromToken(jwt);

            GlobalProfile profile = globalProfileService.getProfileByUserId(userId);
            if (profile == null) {
                return Result.success(null);
            }
            return Result.success(profile);
        } catch (Exception e) {
            return Result.error("获取画像失败");
        }
    }

    @PostMapping
    public Result saveProfile(@RequestHeader("Authorization") String token, @RequestBody GlobalProfile profile) {
        try {
            String jwt = token.replace("Bearer ", "");
            Long userId = jwtUtils.getUserIdFromToken(jwt);

            profile.setUserId(userId);
            GlobalProfile savedProfile = globalProfileService.createOrUpdateProfile(profile);

            return Result.success(savedProfile);
        } catch (Exception e) {
            return Result.error("保存画像失败");
        }
    }

    @PutMapping
    public Result updateProfile(@RequestHeader("Authorization") String token, @RequestBody GlobalProfile profile) {
        try {
            String jwt = token.replace("Bearer ", "");
            Long userId = jwtUtils.getUserIdFromToken(jwt);

            profile.setUserId(userId);
            GlobalProfile updatedProfile = globalProfileService.createOrUpdateProfile(profile);

            return Result.success(updatedProfile);
        } catch (Exception e) {
            return Result.error("更新画像失败");
        }
    }
}
