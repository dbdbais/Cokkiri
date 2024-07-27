package com.ssafy.iscode.problem.controller;

import com.ssafy.iscode.problem.model.dto.Problem;
import com.ssafy.iscode.problem.service.ProblemService;
import com.ssafy.iscode.util.APIConnection;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/problemapi")
public class ProblemRestController {

    ProblemService ps;

    @Autowired
    public ProblemRestController(APIConnection apiConnection, ProblemService ps) {
        this.ps = ps;
    }

    @GetMapping("")
    public String getProblem(@RequestParam @Nullable Long id){
        //if there is no id fetch all problem
        if(id == null){
            return ps.getAllProblem().toString();
        }
        //if there is id fetch problem that matches id
        else{
            return ps.getProblem(id).toString();

        }

    }
    //insert the problem
    @PostMapping("/insert")
    public String insert(@RequestParam Long id) throws IOException {
        return ps.insertProblem(id).toString();
    }
}
