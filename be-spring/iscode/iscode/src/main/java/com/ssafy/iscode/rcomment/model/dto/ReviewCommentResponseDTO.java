package com.ssafy.iscode.rcomment.model.dto;

import com.ssafy.iscode.user.model.dto.User;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
public class ReviewCommentResponseDTO {
    private Long id;
    private String content;
    private User writer;
    private boolean isDeleted;
    private List<ReviewCommentResponseDTO> children = new ArrayList<>();

    public ReviewCommentResponseDTO(Long id, User writer, String content,boolean isDeleted) {
        this.id = id;
        this.writer = writer;
        this.content = content;
        this.isDeleted = isDeleted;
    }
}
