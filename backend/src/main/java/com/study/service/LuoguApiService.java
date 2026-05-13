package com.study.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.config.LuoguConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Slf4j
@Service
@RequiredArgsConstructor
public class LuoguApiService {

    private final LuoguConfig luoguConfig;
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    private HttpHeaders createAuthHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Accept", "application/json");
        headers.set("User-Agent", "study-assistance/1.0");

        if (luoguConfig.getToken() != null && luoguConfig.getToken().contains(":")) {
            String encoded = Base64.getEncoder()
                    .encodeToString(luoguConfig.getToken().getBytes(StandardCharsets.UTF_8));
            headers.set("Authorization", "Basic " + encoded);
        }

        return headers;
    }

    public JsonNode submitJudge(String pid, String lang, String code, boolean o2, String trackId) {
        String url = luoguConfig.getBaseUrl() + "/judge/problem";

        try {
            var bodyNode = objectMapper.createObjectNode();
            bodyNode.put("pid", pid);
            bodyNode.put("lang", lang);
            bodyNode.put("code", code);
            bodyNode.put("o2", o2);
            if (trackId != null) {
                bodyNode.put("trackId", trackId);
            }

            HttpEntity<String> entity = new HttpEntity<>(bodyNode.toString(), createAuthHeaders());
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                return objectMapper.readTree(response.getBody());
            }
        } catch (Exception e) {
            log.error("提交评测失败: {}", e.getMessage());
            throw new RuntimeException("提交评测失败: " + e.getMessage());
        }

        throw new RuntimeException("提交评测失败");
    }

    public JsonNode queryJudgeResult(String requestId) {
        String url = luoguConfig.getBaseUrl() + "/judge/result?id=" + requestId;

        try {
            HttpEntity<Void> entity = new HttpEntity<>(createAuthHeaders());
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                return objectMapper.readTree(response.getBody());
            }
            if (response.getStatusCode() == HttpStatus.NO_CONTENT) {
                return null;
            }
        } catch (Exception e) {
            log.error("查询评测结果失败: {}", e.getMessage());
            throw new RuntimeException("查询评测结果失败: " + e.getMessage());
        }

        return null;
    }

    public JsonNode queryQuotaAvailable() {
        String url = luoguConfig.getBaseUrl() + "/judge/quotaAvailable";

        try {
            HttpEntity<Void> entity = new HttpEntity<>(createAuthHeaders());
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                return objectMapper.readTree(response.getBody());
            }
        } catch (Exception e) {
            log.error("查询额度失败: {}", e.getMessage());
            throw new RuntimeException("查询额度失败: " + e.getMessage());
        }

        throw new RuntimeException("查询额度失败");
    }
}