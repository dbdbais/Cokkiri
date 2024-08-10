package com.ssafy.iscode.submit.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubmitResponseDTO {
    private boolean isCorrect;
    Map<Integer,Boolean> result = new HashMap<>();


}
