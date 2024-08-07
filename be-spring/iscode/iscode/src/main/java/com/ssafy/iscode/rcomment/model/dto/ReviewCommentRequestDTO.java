package com.ssafy.iscode.rcomment.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewCommentRequestDTO {
    private String userId;
    private Long reviewId;
    private Long parentId;
    private String content;

    public ReviewCommentRequestDTO() {
    }


}
