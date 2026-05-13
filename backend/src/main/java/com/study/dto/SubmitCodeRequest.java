package com.study.dto;

import lombok.Data;

@Data
public class SubmitCodeRequest {
    private String pid;
    private String lang;
    private String code;
    private Boolean o2;
    private String trackId;
}