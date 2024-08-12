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
    boolean isCorrect;
    Map<Integer,String> tcOutput = new HashMap<>();
    Map<Integer,Boolean> result = new HashMap<>();
    String opt =null;
    double accuracy;


    public SubmitResponseDTO() {
    }

    public SubmitResponseDTO(boolean isCorrect, Map<Integer, Boolean> result) {
        this.isCorrect = isCorrect;
        this.result = result;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    public Map<Integer, String> getTcOutput() {
        return tcOutput;
    }

    public void setTcOutput(Map<Integer, String> tcOutput) {
        this.tcOutput = tcOutput;
    }

    public Map<Integer, Boolean> getResult() {
        return result;
    }

    public void setResult(Map<Integer, Boolean> result) {
        this.result = result;
    }

    public String getOpt() {
        return opt;
    }

    public void setOpt(String opt) {
        this.opt = opt;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }
}
