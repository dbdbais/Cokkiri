package com.ssafy.iscode.submit.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter

public class SubmitResponseDTO {
    private boolean isCorrect;
    Map<Integer,Boolean> result = new HashMap<>();
    private String output =null;

    public SubmitResponseDTO(boolean isCorrect, Map<Integer, Boolean> result) {
        this.isCorrect = isCorrect;
        this.result = result;
    }

    public SubmitResponseDTO(String output) {
        this.output = output;
    }
}
