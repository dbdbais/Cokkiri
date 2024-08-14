package com.ssafy.iscode.submit.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SCodeResponseDTO {
    private String code;
    private LocalDateTime createdTime;

    @Override
    public String toString() {
        return "SCodeResponseDTO{" +
                "code='" + code + '\'' +
                ", createdTime=" + createdTime +
                '}';
    }
}
