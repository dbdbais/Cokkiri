package com.ssafy.iscode.mail.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class VerificationCode {
    private final int code;
    private final LocalDateTime expirationTime;

    public VerificationCode(int code, LocalDateTime expirationTime) {
        this.code = code;
        this.expirationTime = expirationTime;
    }

    @Override
    public String toString() {
        return "VerificationCode{" +
                "code=" + code +
                ", expirationTime=" + expirationTime +
                '}';
    }
}
