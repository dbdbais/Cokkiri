package com.ssafy.iscode.submit.model.dto;

public class SubmitRequestDTO {

    private Long study_id;
    private Long algo_num;
    private String user_id;
    private String submit_code;
    private boolean correct;

    public SubmitRequestDTO() {}

    public SubmitRequestDTO(Long study_id, Long algo_num, String user_id, String submit_code) {
        this.study_id = study_id;
        this.algo_num = algo_num;
        this.user_id = user_id;
        this.submit_code = submit_code;
    }

    public Long getStudy_id() {
        return study_id;
    }

    public void setStudy_id(Long study_id) {
        this.study_id = study_id;
    }

    public Long getAlgo_num() {
        return algo_num;
    }

    public void setAlgo_num(Long algo_num) {
        this.algo_num = algo_num;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getSubmit_code() {
        return submit_code;
    }

    public void setSubmit_code(String submit_code) {
        this.submit_code = submit_code;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }
}
