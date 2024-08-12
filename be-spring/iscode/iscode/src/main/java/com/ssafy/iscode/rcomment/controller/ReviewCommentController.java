package com.ssafy.iscode.rcomment.controller;

import com.ssafy.iscode.rcomment.model.dto.ReviewComment;
import com.ssafy.iscode.rcomment.model.dto.ReviewCommentRequestDTO;
import com.ssafy.iscode.rcomment.model.dto.ReviewCommentResponseDTO;
import com.ssafy.iscode.rcomment.service.ReviewCommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class ReviewCommentController {
    private final ReviewCommentService reviewCommentService;

    public ReviewCommentController(ReviewCommentService reviewCommentService) {
        this.reviewCommentService = reviewCommentService;
    }

    @PostMapping("/insert")
    public int insert(@RequestBody ReviewCommentRequestDTO reviewCommentRequestDTO){
        return reviewCommentService.saveComment(reviewCommentRequestDTO);
    }

    @PutMapping("/modify")
    public int modify(@RequestParam Long cid, @RequestParam String content){
        return reviewCommentService.modifyComment(cid,content);
    }

    @DeleteMapping("/{cid}")
    public int delete(@PathVariable Long cid){
        return reviewCommentService.deleteComment(cid);
    }

    @GetMapping("/{rid}")
    public List<ReviewCommentResponseDTO> getListByReviewId(@PathVariable Long rid){
        return reviewCommentService.findAll(rid);
    }


}
