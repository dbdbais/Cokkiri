package com.ssafy.iscode.problem.model.dto;


import jakarta.persistence.*;

import java.util.List;
@Entity
@Table(name="Algo")
public class Problem {
    @Id
    @Column(name = "algo_num")
    private Long no;

    @Column(name = "algo_type")
    private List<String> algoType;
    @Column(name = "algo_title")
    private String title;
    @Column(name = "algo_level")
    private int level;
    @Column(name = "algo_info" ,columnDefinition = "LONGTEXT")
    private String info;
    @Column(name="algo_percent")
    private double algoPercent;

    @Column(name = "algo_input")
    private List<String> algoInput;

    @Column(name = "algo_output")
    private List<String> algoOutput;

    public Problem() {
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

    public double getAlgoPercent() {
        return algoPercent;
    }

    public void setAlgoPercent(double algoPercent) {
        this.algoPercent = algoPercent;
    }

    public List<String> getAlgoInput() {
        return algoInput;
    }

    public void setAlgoInput(List<String> algoInput) {
        this.algoInput = algoInput;
    }

    public List<String> getAlgoOutput() {
        return algoOutput;
    }

    public void setAlgoOutput(List<String> algoOutput) {
        this.algoOutput = algoOutput;
    }

    @Override
    public String toString() {
        return "Problem{" +
                "no=" + no +
                ", algoType=" + algoType +
                ", title='" + title + '\'' +
                ", level=" + level +
                ", info='" + info + '\'' +
                ", algoPercent=" + algoPercent +
                ", algoInput=" + algoInput +
                ", algoOutput=" + algoOutput +
                '}';
    }
}