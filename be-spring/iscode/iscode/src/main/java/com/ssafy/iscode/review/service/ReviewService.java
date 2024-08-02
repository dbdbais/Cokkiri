package com.ssafy.iscode.review.service;


import com.ssafy.iscode.review.model.dao.ReviewRepository;
import com.ssafy.iscode.review.model.dto.Review;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public List<Review> getReviews(Long pid){
        return reviewRepository.findAllReviewByPID(pid);
    }

    public int insertReview(Review review){
        return reviewRepository.save(review);
    }

    public int deleteReview(Long rid){
        return reviewRepository.remove(rid);
    }

}
