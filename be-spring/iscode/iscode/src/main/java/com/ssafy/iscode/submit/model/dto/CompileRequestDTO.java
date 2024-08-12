package com.ssafy.iscode.submit.model.dto;

import java.util.Map;

public class CompileRequestDTO {
    public String language;
    public String code;
    public String ipt;
    public Map<Integer, String> input;
    public long time; // 실행 시간 제한 (초 단위)
    public long memory; // 메모리 사용 제한 (메가바이트 단위)

    public CompileRequestDTO(String language, String code, String ipt, Map<Integer, String> input, long time, long memory) {
        this.language = language;
        this.code = code;
        this.ipt = ipt;
        this.input = input;
        this.time = time;
        this.memory = memory;
    }

    @Override
    public String toString() {
        return "CompileRequest{" +
                "language='" + language + '\'' +
                ", code='" + code + '\'' +
                ", ipt='" + ipt + '\'' +
                ", input=" + input +
                ", time=" + time +
                ", memory=" + memory +
                '}';
    }
}
