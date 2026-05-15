package com.study.service;

import com.study.entity.Question;
import com.study.entity.QuestionBank;
import com.study.mapper.QuestionBankMapper;
import com.study.mapper.QuestionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class QuestionBankService {

    private final QuestionBankMapper questionBankMapper;
    private final QuestionMapper questionMapper;

    public List<QuestionBank> getAllBanks() {
        return questionBankMapper.findAll();
    }

    public List<Question> getQuestionsByBankId(Integer bankId) {
        return questionMapper.findByBankId(bankId);
    }

    public Question getQuestionById(Long id) {
        return questionMapper.findById(id).orElse(null);
    }

    public long getQuestionCountByBankId(Integer bankId) {
        return questionMapper.countByBankId(bankId);
    }

    public Map<String, Object> getBankDetail(Integer bankId) {
        QuestionBank bank = questionBankMapper.findById(bankId).orElse(null);
        if (bank == null) {
            return null;
        }

        List<Question> allQuestions = questionMapper.findByBankId(bankId);
        
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> bankInfo = new HashMap<>();
        bankInfo.put("id", bank.getId());
        bankInfo.put("name", bank.getName());
        bankInfo.put("description", bank.getDescription());
        result.put("bank", bankInfo);
        result.put("totalQuestions", allQuestions.size());
        result.put("singleCount", countByType(allQuestions, "single"));
        result.put("multipleCount", countByType(allQuestions, "multiple"));
        result.put("judgeCount", countByType(allQuestions, "judge"));
        result.put("fillCount", countByType(allQuestions, "fill"));
        result.put("essayCount", countByType(allQuestions, "essay"));
        
        return result;
    }

    private long countByType(List<Question> questions, String type) {
        return questions.stream().filter(q -> type.equals(q.getType())).count();
    }

    public Map<String, Object> submitAnswer(Long userId, Long questionId, String answer) {
        Question question = questionMapper.findById(questionId).orElse(null);
        if (question == null) {
            return null;
        }

        Map<String, Object> result = new HashMap<>();
        boolean isCorrect = normalizeAnswer(answer).equals(normalizeAnswer(question.getAnswer()));
        
        result.put("isCorrect", isCorrect);
        result.put("scoreEarned", isCorrect ? question.getScore() : 0);
        result.put("correctAnswer", question.getAnswer());
        result.put("explanation", question.getExplanation());
        
        return result;
    }

    private String normalizeAnswer(String answer) {
        return answer == null ? "" : answer.trim().toUpperCase().replaceAll("\\s+", "");
    }

    public List<Map<String, Object>> getCategories() {
        List<QuestionBank> banks = questionBankMapper.findAll();
        
        Map<String, Map<String, Object>> categoryMap = new HashMap<>();
        
        String[][] majors = {
            {"计算机", "computer", "science"},
            {"数学", "math", "science"},
            {"物理", "physics", "science"},
            {"化学", "chemistry", "science"},
            {"生物", "biology", "science"},
            {"经济", "economics", "economics"},
            {"管理", "management", "management"},
            {"法律", "law", "law"},
            {"教育", "education", "education"},
            {"文学", "literature", "literature"}
        };

        for (String[] major : majors) {
            String name = major[0];
            String id = major[1];
            String icon = major[2];
            
            Map<String, Object> group = new HashMap<>();
            group.put("name", name);
            group.put("icon", icon);
            group.put("subCategories", new ArrayList<>());
            categoryMap.put(name, group);
        }

        for (QuestionBank bank : banks) {
            String category = bank.getCategory();
            String categoryName = mapCategoryToChinese(category);
            
            if (categoryMap.containsKey(categoryName)) {
                Map<String, Object> group = categoryMap.get(categoryName);
                List<Map<String, Object>> subs = (List<Map<String, Object>>) group.get("subCategories");
                
                Map<String, Object> sub = new HashMap<>();
                sub.put("id", "bank-" + bank.getId());
                sub.put("name", bank.getName());
                sub.put("description", bank.getDescription());
                sub.put("questionCount", questionMapper.countByBankId(bank.getId()));
                subs.add(sub);
            }
        }

        return categoryMap.values().stream().toList();
    }

    private String mapCategoryToChinese(String category) {
        Map<String, String> map = new HashMap<>();
        map.put("计算机", "计算机");
        map.put("computer", "计算机");
        map.put("数学", "数学");
        map.put("math", "数学");
        map.put("physics", "物理");
        map.put("chemistry", "化学");
        map.put("biology", "生物");
        map.put("economics", "经济");
        map.put("management", "管理");
        map.put("law", "法律");
        map.put("education", "教育");
        map.put("literature", "文学");
        return map.getOrDefault(category, "计算机");
    }
}
