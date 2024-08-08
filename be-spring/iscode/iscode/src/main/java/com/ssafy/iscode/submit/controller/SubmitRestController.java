package com.ssafy.iscode.submit.controller;

import com.ssafy.iscode.problem.model.dto.Problem;
import com.ssafy.iscode.problem.service.ProblemService;
import com.ssafy.iscode.study.model.dto.StudyDto;
import com.ssafy.iscode.study.service.StudyService;
import com.ssafy.iscode.submit.model.dto.Submit;
import com.ssafy.iscode.submit.model.dto.SubmitRequestDTO;
import com.ssafy.iscode.submit.service.SubmitService;
import com.ssafy.iscode.user.model.dto.User;
import com.ssafy.iscode.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/submitapi")
public class SubmitRestController {

    private final SubmitService submitService;
    private final ProblemService problemService;
    private final UserService userService;
    @Autowired
    public SubmitRestController(SubmitService submitService, ProblemService problemService, UserService userService, StudyService studyService) {
        this.submitService = submitService;
        this.problemService = problemService;
        this.userService = userService;
    }

    @PostMapping
    public int insert(@RequestBody SubmitRequestDTO submitRequestDTO){
        // submit.setCorrect(submitRequestDTO.isCorrect()); 컴파일러로 채점 후에
        boolean sCorrect = true;

        Problem sProblem = problemService.getProblem(submitRequestDTO.getAlgo_num());
        //System.out.println(sProblem);
        User sUser = userService.getUser(submitRequestDTO.getUser_id());
        //System.out.println(sUser);

        Submit submit = new Submit(sProblem,sUser,submitRequestDTO.getSubmit_code(),sCorrect);

        return submitService.insertSubmit(submit);
    }




}
