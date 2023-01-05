package ru.sargassov.fmweb.dto.text_responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TextResponse {
    private String response;
    private Boolean condition;

    public TextResponse (String s) {
        this.response = s;
    }
}
