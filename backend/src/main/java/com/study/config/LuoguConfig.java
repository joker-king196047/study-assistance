package com.study.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "luogu")
public class LuoguConfig {
    private String baseUrl = "https://open-v1.lgapi.cn";
    private String token;
}