package ru.sargassov.fmweb.dto.text_responses;

import lombok.Data;

import java.util.List;

@Data
public class PostMatchDto {
    private String match;
    private List<String> goals;
}
