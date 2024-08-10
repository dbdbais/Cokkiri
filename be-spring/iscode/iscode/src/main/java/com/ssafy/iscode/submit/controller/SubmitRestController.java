package com.ssafy.iscode.submit.controller;

import com.ssafy.iscode.problem.model.dto.Problem;
import com.ssafy.iscode.problem.service.ProblemService;
import com.ssafy.iscode.study.model.dto.StudyDto;
import com.ssafy.iscode.study.service.StudyService;
import com.ssafy.iscode.submit.model.dto.CompileRequestDTO;
import com.ssafy.iscode.submit.model.dto.Submit;
import com.ssafy.iscode.submit.model.dto.SubmitRequestDTO;
import com.ssafy.iscode.submit.model.dto.SubmitResponseDTO;
import com.ssafy.iscode.submit.service.SubmitService;
import com.ssafy.iscode.user.model.dto.User;
import com.ssafy.iscode.user.service.UserService;
import com.ssafy.iscode.util.APIConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/submitapi")
public class SubmitRestController {

    private final SubmitService submitService;
    private final ProblemService problemService;
    private final UserService userService;
    private final APIConnection apiConnection;

    @Autowired
    public SubmitRestController(SubmitService submitService, ProblemService problemService, UserService userService, StudyService studyService, APIConnection apiConnection) {
        this.submitService = submitService;
        this.problemService = problemService;
        this.userService = userService;
        this.apiConnection = apiConnection;
    }

    @PostMapping
    public SubmitResponseDTO insert(@RequestBody SubmitRequestDTO submitRequestDTO){


        Problem sProblem = problemService.getProblem(submitRequestDTO.getAlgo_num());
        //System.out.println(sProblem);
        User sUser = userService.getUser(submitRequestDTO.getUser_id());
        //System.out.println(sUser);

        String language = submitRequestDTO.getLanguage();
        String code = submitRequestDTO.getSubmit_code();
        String ipt = submitRequestDTO.getIpt();

        Map<Integer, String> input = sProblem.getAlgoInput();
        Map<Integer, String> hInput = new HashMap<>();
        if(submitRequestDTO.isSubmit()){
            hInput = sProblem.getAlgoHiddenInput();
            int nxtIdx = input.size();

            for(String val : hInput.values()){
                input.put(++nxtIdx,val);
            }
            //add hidden input value
        }

        long time = sProblem.getTime();
        long memory = sProblem.getMemory();

        CompileRequestDTO compileRequest = new CompileRequestDTO(
                language,
                code,
                ipt,
                input,
                time,
                memory
        );
        Map<Integer,String> coutput = apiConnection.compileCode(compileRequest,submitRequestDTO.isSubmit());
        //apiConnection

        Map<Integer,String> output = sProblem.getAlgoOutput();

        boolean isCorrect = true;
        Map<Integer,Boolean> cResult = new HashMap<>();
        for(int idx : coutput.keySet()){
            String cop = coutput.get(idx);
            String op = output.get(idx);
            System.out.println(idx +" : " + cop +" = " + op);
            if(cop.equals(op)){
                cResult.put(idx,true);
            }
            else{
                cResult.put(idx,false);
                isCorrect = false;
            }
        }
        System.out.println("RESULT");
        for(int idx : cResult.keySet()){
            System.out.println(idx +" : " + cResult.get(idx));
        }

        Submit submit = new Submit(sProblem,sUser,submitRequestDTO.getSubmit_code(),isCorrect);
        submitService.insertSubmit(submit);
        SubmitResponseDTO submitResponseDTO = new SubmitResponseDTO(isCorrect,cResult);

        return submitResponseDTO;
    }

    @GetMapping
    public int getSolved(@RequestParam String userId){
        return submitService.getSolvedProblem(userId);
    }


}
