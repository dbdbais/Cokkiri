package com.ssafy.iscode.problem.model.dto;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;

import java.io.IOException;
import java.util.*;

@Entity
@Table(name="Algo")
public class Problem {

    @Id
    @Column(name = "algo_num")
    private Long no;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "algo_type", joinColumns = @JoinColumn(name = "algo_num"))
    @Column(name = "algo_type")
    private List<String> algoType;

    @Column(name = "algo_title")
    private String title;
    @Column(name = "algo_level")
    private int level;

    @Column(name = "algo_time")
    private int time;
    @Column(name = "algo_memory")
    private int memory;

    @Column(name ="algo_text" , columnDefinition = "LONGTEXT")
    private String text;

    @Column(name = "algo_info" ,columnDefinition = "LONGTEXT")
    private String info;
    @Column(name="algo_percent")
    private double algoPercent;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "algo_input", joinColumns = @JoinColumn(name = "algo_num"))
    @Column(name = "algo_input_value",columnDefinition = "LONGTEXT")
    private Map<Integer, String> algoInput;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "algo_output", joinColumns = @JoinColumn(name = "algo_num"))
    @Column(name = "algo_output_value",columnDefinition = "LONGTEXT")
    private Map<Integer, String> algoOutput;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "algo_hidden_input", joinColumns = @JoinColumn(name = "algo_num"))
    @Column(name = "hidden_input_value",columnDefinition = "LONGTEXT")
    private Map<Integer, String> algoHiddenInput;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "algo_hidden_output", joinColumns = @JoinColumn(name = "algo_num"))
    @Column(name = "hidden_output_value",columnDefinition = "LONGTEXT")
    private Map<Integer, String> algoHiddenOutput;

    public Problem() {
    }

    public Problem(Long no, List<String> algoType, String title, int level, int time, int memory, String text, String info, double algoPercent, Map<Integer, String> algoInput, Map<Integer, String> algoOutput, Map<Integer, String> algoHiddenInput, Map<Integer, String> algoHiddenOutput) {
        this.no = no;
        this.algoType = algoType;
        this.title = title;
        this.level = level;
        this.time = time;
        this.memory = memory;
        this.text = text;
        this.info = info;
        this.algoPercent = algoPercent;
        this.algoInput = algoInput;
        this.algoOutput = algoOutput;
        this.algoHiddenInput = algoHiddenInput;
        this.algoHiddenOutput = algoHiddenOutput;
    }

    public void getHidden(String jsonData) {

        try {
            // ObjectMapper 인스턴스 생성
            ObjectMapper objectMapper = new ObjectMapper();
            // JSON 데이터를 JsonNode로 파싱
            JsonNode rootNode = objectMapper.readTree(jsonData);

            // algoHiddenInput과 algoHiddenOutput 노드 추출
            JsonNode inputNode = rootNode.path("algoHiddenInput");
            JsonNode outputNode = rootNode.path("algoHiddenOutput");

            Iterator<Map.Entry<String, JsonNode>> inputFields = inputNode.fields();

            int iidx = 1;
            while (inputFields.hasNext()) {
                Map.Entry<String, JsonNode> field = inputFields.next();
                algoHiddenInput.put(iidx++, field.getValue().asText());
            }

            Iterator<Map.Entry<String, JsonNode>> outputFields = outputNode.fields();
            int oidx = 1;
            while (outputFields.hasNext()) {
                Map.Entry<String, JsonNode> field = outputFields.next();
                algoHiddenOutput.put(oidx++, field.getValue().asText());
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        // 결과 출력
        System.out.println("Input List: " + algoHiddenInput);
        System.out.println("Output List: " + algoHiddenOutput);

    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getMemory() {
        return memory;
    }

    public void setMemory(int memory) {
        this.memory = memory;
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

    public Map<Integer, String> getAlgoInput() {
        return algoInput;
    }

    public void setAlgoInput(Map<Integer, String> algoInput) {
        this.algoInput = algoInput;
    }

    public Map<Integer, String> getAlgoOutput() {
        return algoOutput;
    }

    public void setAlgoOutput(Map<Integer, String> algoOutput) {
        this.algoOutput = algoOutput;
    }

    public Map<Integer, String> getAlgoHiddenInput() {
        return algoHiddenInput;
    }

    public void setAlgoHiddenInput(Map<Integer, String> algoHiddenInput) {
        this.algoHiddenInput = algoHiddenInput;
    }

    public Map<Integer, String> getAlgoHiddenOutput() {
        return algoHiddenOutput;
    }

    public void setAlgoHiddenOutput(Map<Integer, String> algoHiddenOutput) {
        this.algoHiddenOutput = algoHiddenOutput;
    }

    @Override
    public String toString() {
        return "Problem{" +
                "no=" + no +
                ", algoType=" + algoType +
                ", title='" + title + '\'' +
                ", level=" + level +
                ", time=" + time +
                ", memory=" + memory +
                ", text='" + text + '\'' +
                ", info='" + info + '\'' +
                ", algoPercent=" + algoPercent +
                ", algoInput=" + algoInput +
                ", algoOutput=" + algoOutput +
                ", algoHiddenInput=" + algoHiddenInput +
                ", algoHiddenOutput=" + algoHiddenOutput +
                '}';
    }
}