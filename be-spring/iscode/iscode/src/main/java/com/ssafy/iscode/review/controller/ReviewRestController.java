package com.ssafy.iscode.review.controller;

import com.ssafy.iscode.problem.model.dto.Problem;
import com.ssafy.iscode.problem.service.ProblemService;
import com.ssafy.iscode.review.model.dto.Review;
import com.ssafy.iscode.review.model.dto.ReviewRequestDTO;
import com.ssafy.iscode.review.service.ReviewService;
import com.ssafy.iscode.user.model.dto.User;
import com.ssafy.iscode.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviewapi")
public class ReviewRestController {

    private ReviewService reviewService;

    private ProblemService problemService;

    private UserService userService;

    @Autowired
    public ReviewRestController(ReviewService reviewService, ProblemService problemService, UserService userService) {
        this.reviewService = reviewService;
        this.problemService = problemService;
        this.userService = userService;
    }

    @PostMapping("/create")
    public int create(@RequestBody ReviewRequestDTO reviewRequestDTO){
        //System.out.println(reviewRequestDTO);

        Review review = new Review();
        User gUser = userService.getUser(reviewRequestDTO.getUser());
        Problem gProblem = problemService.getProblem(reviewRequestDTO.getProblem());

        review.setCode(reviewRequestDTO.getCode());
        review.setContent(reviewRequestDTO.getContent());
        review.setUser(gUser);
        review.setProblem(gProblem);
        //set user & problem

        //System.out.println(review);

        return reviewService.insertReview(review);
    }

    @GetMapping("/{pid}")
    public List<Review> getAllReviews(@PathVariable Long pid){
        return reviewService.getReviews(pid);
    }

    @PutMapping("/modify")
    public int modify(@RequestBody Review review){
        return reviewService.insertReview(review);
    }

    @DeleteMapping("/delete/{rid}")
    public int delReview(@PathVariable Long rid){
        return reviewService.deleteReview(rid);
    }

}
