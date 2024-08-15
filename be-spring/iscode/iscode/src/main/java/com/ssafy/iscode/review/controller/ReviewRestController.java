package com.ssafy.iscode.review.controller;

import com.ssafy.iscode.problem.model.dto.Problem;
import com.ssafy.iscode.problem.service.ProblemService;
import com.ssafy.iscode.review.model.dto.Review;
import com.ssafy.iscode.review.model.dto.ReviewRequestDTO;
import com.ssafy.iscode.review.service.ReviewService;
import com.ssafy.iscode.user.model.dto.User;
import com.ssafy.iscode.user.service.UserService;
import com.ssafy.iscode.util.APIConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/reviewapi")
public class ReviewRestController {

    private final ReviewService reviewService;

    private final ProblemService problemService;

    private final UserService userService;

    private final APIConnection apiConnection;

    @Autowired
    public ReviewRestController(ReviewService reviewService, ProblemService problemService, UserService userService, APIConnection apiConnection) {
        this.reviewService = reviewService;
        this.problemService = problemService;
        this.userService = userService;
        this.apiConnection = apiConnection;
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

    @PostMapping("/auto")
    public String autoCreate(@RequestBody String submitCode) throws IOException {
        return apiConnection.useOpenAI(submitCode,"유저가 제출한 코드를 보고 어떤 로직으로 동작하였고, 어떤 자료구조를 썼으며, 어떠한 알고리즘을 사용했는지 리뷰를 작성해줘.");
    }
}
