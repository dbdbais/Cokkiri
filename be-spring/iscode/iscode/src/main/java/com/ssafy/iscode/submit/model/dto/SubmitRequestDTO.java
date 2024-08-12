package com.ssafy.iscode.submit.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubmitRequestDTO {

    private Long algo_num;
    private String user_id;
    private String submit_code;
    private boolean correct;

    public SubmitRequestDTO() {}

    public SubmitRequestDTO(Long algo_num, String user_id, String submit_code) {
        this.algo_num = algo_num;
        this.user_id = user_id;
        this.submit_code = submit_code;
    }


}
