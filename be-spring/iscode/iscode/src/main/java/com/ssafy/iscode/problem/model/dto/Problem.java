package com.ssafy.iscode.problem.model.dto;


import jakarta.persistence.*;

import java.util.List;
@Entity
@Table(name="Algo")
public class Problem {
    @Id
    @Column(name = "algo_num")
    private Long no;

    private List<String> algoType;
    @Column(name = "algo_title")
    private String title;
    @Column(name = "algo_level")
    private int level;
    @Column(name = "algo_info" ,columnDefinition = "LONGTEXT")
    private String info;
    @Column(name="algo_tries")
    private double tries;

    @Column(name = "algo_input")
    private List<String> input;

    @Column(name = "algo_output")
    private List<String> output;

    public Problem() {
    }

    public Problem(Long no, List<String> algoType, String title, int level, String info, double tries, List<String> input, List<String> output) {
        this.no = no;
        this.algoType = algoType;
        this.title = title;
        this.level = level;
        this.info = info;
        this.tries = tries;
        this.input = input;
        this.output = output;
    }

    public Long getNo() {
        return no;
    }

    public void setNo(Long no) {
        this.no = no;
    }

    public List<String> getAlgoType() {
        return algoType;
    }

    public void setAlgoType(List<String> algoType) {
        this.algoType = algoType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public double getTries() {
        return tries;
    }

    public void setTries(double tries) {
        this.tries = tries;
    }

    public List<String> getInput() {
        return input;
    }

    public void setInput(List<String> input) {
        this.input = input;
    }

    public List<String> getOutput() {
        return output;
    }

    public void setOutput(List<String> output) {
        this.output = output;
    }
}