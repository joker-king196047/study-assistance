package com.study.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.study.common.Result;
import com.study.dto.CategoryGroup;
import com.study.dto.SubmitCodeRequest;
import com.study.entity.Question;
import com.study.mapper.QuestionMapper;
import com.study.service.LuoguApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/question-bank")
@RequiredArgsConstructor
public class QuestionBankController {

    private final QuestionMapper questionMapper;
    private final LuoguApiService luoguApiService;

    @GetMapping("/categories")
    public Result<List<CategoryGroup>> getCategories() {
        List<CategoryGroup> categories = new ArrayList<>();

        categories.add(new CategoryGroup("哲学", "philosophy", java.util.Arrays.asList(
                new CategoryGroup.SubCategory("philosophy", "哲学类", "哲学、逻辑学、宗教学、伦理学", 0)
        )));

        categories.add(new CategoryGroup("经济学", "economics", java.util.Arrays.asList(
                new CategoryGroup.SubCategory("econ-theory", "经济学类", "经济学、经济统计学、数字经济等", 0),
                new CategoryGroup.SubCategory("econ-finance", "金融学类", "金融学、金融工程、保险学、投资学等", 0),
                new CategoryGroup.SubCategory("econ-trade", "经济与贸易类", "国际经济与贸易、贸易经济等", 0),
                new CategoryGroup.SubCategory("econ-fiscal", "财政学类", "财政学、税收学等", 0)
        )));

        categories.add(new CategoryGroup("法学", "law", java.util.Arrays.asList(
                new CategoryGroup.SubCategory("law", "法学类", "法学、知识产权、国际法、纪检监察等", 0),
                new CategoryGroup.SubCategory("politics", "政治学类", "政治学与行政学、国际政治、外交学等", 0),
                new CategoryGroup.SubCategory("sociology", "社会学类", "社会学、社会工作、人类学等", 0),
                new CategoryGroup.SubCategory("marxism", "马克思主义理论类", "科学社会主义、思想政治教育等", 0)
        )));

        categories.add(new CategoryGroup("教育学", "education", java.util.Arrays.asList(
                new CategoryGroup.SubCategory("edu-theory", "教育学类", "教育学、学前教育、小学教育、特殊教育等", 0),
                new CategoryGroup.SubCategory("edu-sport", "体育学类", "体育教育、运动训练、社会体育指导等", 0)
        )));

        categories.add(new CategoryGroup("文学", "literature", java.util.Arrays.asList(
                new CategoryGroup.SubCategory("chinese-lang", "中国语言文学类", "汉语言文学、汉语国际教育、古典文献学等", 0),
                new CategoryGroup.SubCategory("foreign-lang", "外国语言文学类", "英语、日语、翻译、商务英语等", 0),
                new CategoryGroup.SubCategory("journalism", "新闻传播学类", "新闻学、广播电视学、广告学、传播学等", 0)
        )));

        categories.add(new CategoryGroup("历史学", "history", java.util.Arrays.asList(
                new CategoryGroup.SubCategory("history", "历史学类", "历史学、考古学、文物与博物馆学等", 0)
        )));

        categories.add(new CategoryGroup("理学", "science", java.util.Arrays.asList(
                new CategoryGroup.SubCategory("math", "数学类", "数学与应用数学、信息与计算科学、数理基础科学等", 0),
                new CategoryGroup.SubCategory("physics", "物理学类", "物理学、应用物理学、核物理等", 0),
                new CategoryGroup.SubCategory("chemistry", "化学类", "化学、应用化学、化学生物学等", 0),
                new CategoryGroup.SubCategory("statistics", "统计学类", "统计学、应用统计学等", 0),
                new CategoryGroup.SubCategory("psychology", "心理学类", "心理学、应用心理学等", 0),
                new CategoryGroup.SubCategory("biology", "生物科学类", "生物科学、生物技术、生态学等", 0),
                new CategoryGroup.SubCategory("geography", "地理科学类", "地理科学、自然地理与资源环境、人文地理等", 0)
        )));

        categories.add(new CategoryGroup("工学", "engineering", java.util.Arrays.asList(
                new CategoryGroup.SubCategory("cs-data-structure", "计算机类", "数据结构与算法、程序设计、软件工程等", 0),
                new CategoryGroup.SubCategory("cs-os", "操作系统", "进程管理、内存管理、文件系统", 0),
                new CategoryGroup.SubCategory("cs-network", "计算机网络", "TCP/IP、HTTP、网络安全", 0),
                new CategoryGroup.SubCategory("cs-database", "数据库原理", "SQL、关系代数、事务管理", 0),
                new CategoryGroup.SubCategory("cs-compiler", "编译原理", "词法分析、语法分析、代码优化", 0),
                new CategoryGroup.SubCategory("cs-composition", "计算机组成原理", "CPU、存储器、总线、I/O系统", 0),
                new CategoryGroup.SubCategory("eng-electronic", "电子信息类", "电子信息工程、通信工程、集成电路等", 0),
                new CategoryGroup.SubCategory("eng-automation", "自动化类", "自动化、机器人工程、工业智能等", 0),
                new CategoryGroup.SubCategory("eng-mechanical", "机械类", "机械工程、车辆工程、智能制造等", 0),
                new CategoryGroup.SubCategory("eng-civil", "土木类", "土木工程、建筑环境、给排水科学等", 0),
                new CategoryGroup.SubCategory("eng-electrical", "电气类", "电气工程及其自动化、智能电网等", 0),
                new CategoryGroup.SubCategory("eng-material", "材料类", "材料科学与工程、高分子材料等", 0),
                new CategoryGroup.SubCategory("eng-chemical", "化工与制药类", "化学工程与工艺、制药工程等", 0),
                new CategoryGroup.SubCategory("eng-aerospace", "航空航天类", "航空航天工程、飞行器设计与工程等", 0),
                new CategoryGroup.SubCategory("eng-environmental", "环境科学与工程类", "环境工程、环境科学、环保设备等", 0),
                new CategoryGroup.SubCategory("eng-bioeng", "生物医学工程类", "生物医学工程、医疗器械工程等", 0),
                new CategoryGroup.SubCategory("eng-food", "食品科学与工程类", "食品科学与工程、食品安全等", 0),
                new CategoryGroup.SubCategory("eng-ai", "人工智能类", "人工智能、智能科学与技术、大数据等", 0),
                new CategoryGroup.SubCategory("eng-security", "安全科学与工程类", "安全工程、应急技术与管理等", 0),
                new CategoryGroup.SubCategory("eng-software", "软件工程", "软件设计、系统架构、项目管理", 0)
        )));

        categories.add(new CategoryGroup("农学", "agriculture", java.util.Arrays.asList(
                new CategoryGroup.SubCategory("agri-plant", "植物生产类", "农学、园艺、植物保护、种子科学与工程等", 0),
                new CategoryGroup.SubCategory("agri-animal", "动物医学类", "动物医学、动物药学等", 0),
                new CategoryGroup.SubCategory("agri-forestry", "林学类", "林学、园林、森林保护等", 0),
                new CategoryGroup.SubCategory("agri-fishery", "水产类", "水产养殖学、海洋渔业科学与技术等", 0)
        )));

        categories.add(new CategoryGroup("医学", "medicine", java.util.Arrays.asList(
                new CategoryGroup.SubCategory("med-basic", "基础医学类", "基础医学、生物医学等", 0),
                new CategoryGroup.SubCategory("med-clinical", "临床医学类", "临床医学、麻醉学、医学影像学等", 0),
                new CategoryGroup.SubCategory("med-stomatology", "口腔医学类", "口腔医学", 0),
                new CategoryGroup.SubCategory("med-pharmacy", "药学类", "药学、药物制剂、临床药学等", 0),
                new CategoryGroup.SubCategory("med-tcm", "中医学类", "中医学、针灸推拿学、中西医临床医学等", 0),
                new CategoryGroup.SubCategory("med-nursing", "护理学类", "护理学、助产学等", 0),
                new CategoryGroup.SubCategory("med-public", "公共卫生与预防医学类", "预防医学、食品卫生与营养学等", 0)
        )));

        categories.add(new CategoryGroup("管理学", "management", java.util.Arrays.asList(
                new CategoryGroup.SubCategory("mgmt-science", "管理科学与工程类", "管理科学、信息管理与信息系统、大数据管理等", 0),
                new CategoryGroup.SubCategory("mgmt-business", "工商管理类", "工商管理、会计学、财务管理、人力资源管理等", 0),
                new CategoryGroup.SubCategory("mgmt-public", "公共管理类", "公共事业管理、行政管理、劳动与社会保障等", 0),
                new CategoryGroup.SubCategory("mgmt-ecommerce", "电子商务类", "电子商务、跨境电子商务等", 0),
                new CategoryGroup.SubCategory("mgmt-logistics", "物流管理与工程类", "物流管理、供应链管理等", 0)
        )));

        categories.add(new CategoryGroup("艺术学", "art", java.util.Arrays.asList(
                new CategoryGroup.SubCategory("art-fine", "美术学类", "美术学、绘画、雕塑、书法学等", 0),
                new CategoryGroup.SubCategory("art-design", "设计学类", "艺术设计学、视觉传达设计、环境设计等", 0),
                new CategoryGroup.SubCategory("art-music", "音乐与舞蹈学类", "音乐学、作曲、舞蹈表演、舞蹈编导等", 0),
                new CategoryGroup.SubCategory("art-drama", "戏剧与影视学类", "表演、戏剧学、广播电视编导、播音与主持等", 0)
        )));

        for (CategoryGroup group : categories) {
            for (CategoryGroup.SubCategory sub : group.getSubCategories()) {
                long count = questionMapper.findBySubCategory(sub.getId()).size();
                sub.setQuestionCount((int) count);
            }
        }

        return Result.success(categories);
    }

    @GetMapping("/problems")
    public Result<List<Question>> getProblems(@RequestParam String subCategory) {
        List<Question> questions = questionMapper.findBySubCategory(subCategory);
        return Result.success(questions);
    }

    @GetMapping("/problem/{pid}")
    public Result<Question> getProblemDetail(@PathVariable String pid) {
        Question question = questionMapper.findByPid(pid);
        if (question == null) {
            return Result.error(404, "题目不存在");
        }
        return Result.success(question);
    }

    @PostMapping("/submit")
    public Result<Map<String, String>> submitCode(@RequestBody SubmitCodeRequest request) {
        try {
            JsonNode result = luoguApiService.submitJudge(
                    request.getPid(),
                    request.getLang() != null ? request.getLang() : "cxx/14/gcc",
                    request.getCode(),
                    request.getO2() != null ? request.getO2() : true,
                    request.getTrackId()
            );

            Map<String, String> response = new HashMap<>();
            if (result != null && result.has("requestId")) {
                response.put("requestId", result.get("requestId").asText());
            }
            response.put("status", "submitted");
            return Result.success(response);
        } catch (Exception e) {
            return Result.error("提交评测失败: " + e.getMessage());
        }
    }

    @GetMapping("/judge-result")
    public Result<Map<String, Object>> getJudgeResult(@RequestParam String requestId) {
        try {
            JsonNode result = luoguApiService.queryJudgeResult(requestId);
            if (result == null) {
                Map<String, Object> pending = new HashMap<>();
                pending.put("status", "pending");
                return Result.success(pending);
            }

            Map<String, Object> response = new HashMap<>();
            response.put("status", "completed");
            response.put("type", result.has("type") ? result.get("type").asText() : null);
            response.put("requestId", result.has("requestId") ? result.get("requestId").asText() : null);
            response.put("trackId", result.has("trackId") ? result.get("trackId").asText() : null);

            if (result.has("data")) {
                JsonNode data = result.get("data");
                Map<String, Object> dataMap = new HashMap<>();

                if (data.has("compile")) {
                    JsonNode compile = data.get("compile");
                    Map<String, Object> compileMap = new HashMap<>();
                    compileMap.put("success", compile.has("success") ? compile.get("success").asBoolean() : false);
                    compileMap.put("message", compile.has("message") ? compile.get("message").asText() : "");
                    compileMap.put("opt2", compile.has("opt2") ? compile.get("opt2").asBoolean() : false);
                    dataMap.put("compile", compileMap);
                }

                if (data.has("judge") && !data.get("judge").isNull()) {
                    JsonNode judge = data.get("judge");
                    Map<String, Object> judgeMap = new HashMap<>();
                    judgeMap.put("id", judge.has("id") ? judge.get("id").asInt() : 0);
                    judgeMap.put("status", judge.has("status") ? judge.get("status").asInt() : 0);
                    judgeMap.put("score", judge.has("score") ? judge.get("score").asInt() : 0);
                    judgeMap.put("time", judge.has("time") ? judge.get("time").asInt() : 0);
                    judgeMap.put("memory", judge.has("memory") ? judge.get("memory").asInt() : 0);

                    if (judge.has("subtasks")) {
                        judgeMap.put("subtasks", convertSubtasks(judge.get("subtasks")));
                    }
                    dataMap.put("judge", judgeMap);
                }

                response.put("data", dataMap);
            }

            return Result.success(response);
        } catch (Exception e) {
            return Result.error("查询评测结果失败: " + e.getMessage());
        }
    }

    private List<Map<String, Object>> convertSubtasks(JsonNode subtasks) {
        List<Map<String, Object>> result = new ArrayList<>();
        for (JsonNode subtask : subtasks) {
            Map<String, Object> subtaskMap = new HashMap<>();
            subtaskMap.put("id", subtask.has("id") ? subtask.get("id").asInt() : 0);
            subtaskMap.put("status", subtask.has("status") ? subtask.get("status").asInt() : 0);
            subtaskMap.put("score", subtask.has("score") ? subtask.get("score").asInt() : 0);

            if (subtask.has("cases")) {
                List<Map<String, Object>> cases = new ArrayList<>();
                for (JsonNode testCase : subtask.get("cases")) {
                    Map<String, Object> caseMap = new HashMap<>();
                    caseMap.put("id", testCase.has("id") ? testCase.get("id").asInt() : 0);
                    caseMap.put("status", testCase.has("status") ? testCase.get("status").asInt() : 0);
                    caseMap.put("score", testCase.has("score") ? testCase.get("score").asInt() : 0);
                    caseMap.put("time", testCase.has("time") ? testCase.get("time").asInt() : 0);
                    caseMap.put("memory", testCase.has("memory") ? testCase.get("memory").asInt() : 0);
                    caseMap.put("description", testCase.has("description") ? testCase.get("description").asText() : "");
                    cases.add(caseMap);
                }
                subtaskMap.put("cases", cases);
            }
            result.add(subtaskMap);
        }
        return result;
    }
}