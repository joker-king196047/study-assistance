package com.study.service;

import com.study.common.BusinessException;
import com.study.entity.User;
import com.study.mapper.UserMapper;
import com.study.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public Map<String, Object> register(String username, String email, String password) {
        if (userMapper.existsByUsername(username)) {
            throw new BusinessException(400, "用户名已存在");
        }
        if (userMapper.existsByEmail(email)) {
            throw new BusinessException(400, "邮箱已被注册");
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        userMapper.save(user);

        String token = jwtUtils.generateToken(username);
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("user", getUserInfo(user));
        return result;
    }

    public Map<String, Object> login(String username, String password) {
        User user = userMapper.findByUsername(username)
                .orElseThrow(() -> new BusinessException(401, "用户名或密码错误"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BusinessException(401, "用户名或密码错误");
        }

        String token = jwtUtils.generateToken(username);
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("user", getUserInfo(user));
        return result;
    }

    public Map<String, Object> getUserInfo(String username) {
        User user = userMapper.findByUsername(username)
                .orElseThrow(() -> new BusinessException(401, "用户不存在"));
        return getUserInfo(user);
    }

    private Map<String, Object> getUserInfo(User user) {
        Map<String, Object> info = new HashMap<>();
        info.put("id", user.getId());
        info.put("username", user.getUsername());
        info.put("email", user.getEmail());
        info.put("createTime", user.getCreateTime());
        return info;
    }
}
