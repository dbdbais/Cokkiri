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
        Map<Integer,String> output = sProblem.getAlgoOutput();
        //문제의 인풋, 아웃풋
        Map<Integer,String> finput = new HashMap<>(input);
        Map<Integer,String> foutput = new HashMap<>(output);
        //문제의 최종 인풋, 아웃풋

        Map<Integer, String> hInput,hOutput;
        // hInput과 hOutput

        System.out.println(submitRequestDTO.getIsSubmit());
        if(submitRequestDTO.getIsSubmit() == 1){
            hInput = sProblem.getAlgoHiddenInput();
            hOutput = sProblem.getAlgoHiddenOutput();
            System.out.println("HIDDEN TEST CASE");
            for(int hi : hInput.keySet()){
                System.out.println(hi +" : " + hInput.get(hi));
            }
            System.out.println("============");

            int nxtIdx = input.size()+1;

            for(String val : hInput.values()){
                finput.put(nxtIdx++,val);
            }

            nxtIdx = input.size()+1;
            for(String val : hOutput.values()){
                foutput.put(nxtIdx++,val);
            }
            //add hidden input value
        }

        System.out.println("INPUT");
        for(int idx : finput.keySet()){
            System.out.println(idx +" : " + finput.get(idx));
        }

        long time = sProblem.getTime();
        long memory = sProblem.getMemory();

        CompileRequestDTO compileRequest = new CompileRequestDTO(
                language,
                code,
                ipt,
                finput,
                time,
                memory
        );
        Map<Integer,String> coutput = apiConnection.compileCode(compileRequest,submitRequestDTO.getIsSubmit()==1);
        //apiConnection



        System.out.println("OUTPUT");
        for(int idx : coutput.keySet()){
            System.out.println(idx +" : " + coutput.get(idx));
        }
        System.out.println("=============");


        if(submitRequestDTO.getIsSubmit() == 0) {
            //실행이라면
            if (ipt == null) {
                //인풋이 없다면
                boolean isCorrect = true;
                Map<Integer, Boolean> cResult = new HashMap<>();
                for (int idx : coutput.keySet()) {
                    String cop = coutput.get(idx);
                    String op = foutput.get(idx);
                    System.out.println(idx + " : " + cop + " = " + op);
                    if (cop.equals(op)) {
                        cResult.put(idx, true);
                    } else {
                        cResult.put(idx, false);
                        isCorrect = false;
                    }
                }
                
                SubmitResponseDTO submitResponseDTO = new SubmitResponseDTO();
                submitResponseDTO.setTcOutput(foutput);
                submitResponseDTO.setResult(cResult);
                return submitResponseDTO;
            } else {

                SubmitResponseDTO submitResponseDTO = new SubmitResponseDTO();
                //submitResponseDTO.setTcOutput(coutput);
                submitResponseDTO.setOpt(coutput.get(0));
                
                return submitResponseDTO;
            }
        } else {
            //isSubmit이 1이면 그냥 들어오는것만
            boolean isCorrect = true;
            Map<Integer, Boolean> cResult = new HashMap<>();
            int whole = coutput.size();
            int correctTC = 0;
            for (int idx : coutput.keySet()) {
                String cop = coutput.get(idx);
                String op = foutput.get(idx);
                System.out.println(idx + " : " + cop + " = " + op);
                if (cop.equals(op)) {
                    cResult.put(idx, true);
                    correctTC++;
                } else {
                    cResult.put(idx, false);
                    isCorrect = false;
                }
            }
            System.out.println("RESULT");
            for (int idx : cResult.keySet()) {
                System.out.println(idx + " : " + cResult.get(idx));
            }
            double percent =  correctTC * 1.0 / whole;
            System.out.println(percent);
            Submit submit = new Submit(sProblem, sUser, submitRequestDTO.getSubmit_code(), isCorrect);
            submitService.insertSubmit(submit);
            SubmitResponseDTO submitResponseDTO = new SubmitResponseDTO(isCorrect, cResult);
            submitResponseDTO.setTcOutput(coutput);
            submitResponseDTO.setAccuracy(percent);
            return submitResponseDTO;
        }
    }


    @GetMapping
    public int getSolved(@RequestParam String userId){
        return submitService.getSolvedProblem(userId);
    }


}
