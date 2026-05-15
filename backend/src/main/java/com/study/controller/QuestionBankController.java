package com.study.controller;

import com.study.common.Result;
import com.study.entity.Question;
import com.study.service.QuestionBankService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/question-bank")
@RequiredArgsConstructor
public class QuestionBankController {

    private final QuestionBankService questionBankService;

    @GetMapping("/categories")
    public Result<List<Map<String, Object>>> getCategories() {
        List<Map<String, Object>> categories = questionBankService.getCategories();
        return Result.success(categories);
    }

    @GetMapping("/questions/{bankId}")
    public Result<List<Question>> getQuestions(@PathVariable String bankId) {
        Integer id = Integer.parseInt(bankId.replace("bank-", ""));
        List<Question> questions = questionBankService.getQuestionsByBankId(id);
        return Result.success(questions);
    }

    @GetMapping("/question/{id}")
    public Result<Question> getQuestion(@PathVariable Long id) {
        Question question = questionBankService.getQuestionById(id);
        return question != null ? Result.success(question) : Result.error("题目不存在");
    }

    @GetMapping("/bank/{bankId}")
    public Result<Map<String, Object>> getBankDetail(@PathVariable Integer bankId) {
        Map<String, Object> detail = questionBankService.getBankDetail(bankId);
        return detail != null ? Result.success(detail) : Result.error("题库不存在");
    }

    @PostMapping("/submit")
    public Result<Map<String, Object>> submitAnswer(@RequestBody Map<String, Object> params) {
        Long userId = Long.valueOf(params.get("userId").toString());
        Long questionId = Long.valueOf(params.get("questionId").toString());
        String answer = params.get("answer").toString();
        Map<String, Object> result = questionBankService.submitAnswer(userId, questionId, answer);
        return result != null ? Result.success(result) : Result.error("题目不存在");
    }
}
