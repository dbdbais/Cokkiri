package com.ssafy.iscode.submit.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SubmitRequestDTO {
    private Long algo_num;
    private String user_id;
    private String language;
    private String submit_code;
    private String ipt = null;
    private long time;
    private long memory;
    private boolean isSubmit;



    public SubmitRequestDTO() {}


}
