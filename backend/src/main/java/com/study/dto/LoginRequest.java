package com.study.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;
<<<<<<< HEAD
}
=======
}
>>>>>>> cb0181847d65aa2475010e5d4d79cb286d531fa4
