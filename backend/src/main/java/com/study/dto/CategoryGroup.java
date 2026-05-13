package com.study.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoryGroup {
    private String name;
    private String icon;
    private java.util.List<SubCategory> subCategories;

    @Data
    @AllArgsConstructor
    public static class SubCategory {
        private String id;
        private String name;
        private String description;
        private Integer questionCount;
    }
}