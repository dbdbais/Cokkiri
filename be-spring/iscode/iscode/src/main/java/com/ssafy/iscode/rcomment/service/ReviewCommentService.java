package com.ssafy.iscode.rcomment.service;

import com.ssafy.iscode.rcomment.model.dao.ReviewCommentRepository;
import com.ssafy.iscode.rcomment.model.dto.ReviewComment;
import com.ssafy.iscode.rcomment.model.dto.ReviewCommentRequestDTO;
import com.ssafy.iscode.rcomment.model.dto.ReviewCommentResponseDTO;
import com.ssafy.iscode.review.model.dao.ReviewRepository;
import com.ssafy.iscode.review.model.dto.Review;
import com.ssafy.iscode.user.model.dao.UserRepository;
import com.ssafy.iscode.user.model.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewCommentService {

    private final ReviewCommentRepository reviewCommentRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    @Autowired
    public ReviewCommentService(ReviewCommentRepository reviewCommentRepository, ReviewRepository reviewRepository, UserRepository userRepository) {
        this.reviewCommentRepository = reviewCommentRepository;
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
    }

    public int saveComment(ReviewCommentRequestDTO reviewCommentDTO){

        ReviewComment reviewComment = new ReviewComment();
        ReviewComment parent = null;
        if(reviewCommentDTO.getParentId() != null) {
            parent = reviewCommentRepository.findById(reviewCommentDTO.getParentId());
        }
        Review review = reviewRepository.findById(reviewCommentDTO.getReviewId());
        User user = userRepository.findById(reviewCommentDTO.getUserId());
        String content = reviewCommentDTO.getContent();

        reviewComment.setParent(parent);
        reviewComment.setWriter(user);
        reviewComment.setReview(review);
        reviewComment.setContent(content);

        return reviewCommentRepository.save(reviewComment);
    }

    //modify Comment
    public int modifyComment(Long cid,String comment){
        ReviewComment mComment =reviewCommentRepository.findById(cid);
        mComment.setContent(comment);
        return reviewCommentRepository.modify(mComment);
    }

    public int deleteComment(Long cid){
        ReviewComment dComment =reviewCommentRepository.findById(cid);
        return reviewCommentRepository.deleteComment(dComment);
    }

    public List<ReviewCommentResponseDTO> findAll(Long reviewId){
        return reviewCommentRepository.findAllByReviewId(reviewId);
    }

}
