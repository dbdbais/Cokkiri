package com.ssafy.iscode.problem.controller;

import com.ssafy.iscode.problem.model.dto.Problem;
import com.ssafy.iscode.problem.service.ProblemService;
import com.ssafy.iscode.util.APIConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/problemapi")
public class ProblemRestController {
    APIConnection apiConnection;

    ProblemService ps;

    @Autowired
    public ProblemRestController(APIConnection apiConnection, ProblemService ps) {
        this.apiConnection = apiConnection;
        this.ps = ps;
    }

    @GetMapping("/init")
    public void init(){
        System.out.println("asd");
    }

    @GetMapping("/insert")
    public Problem insert(@RequestParam Long id) throws IOException {
        System.out.println("called");
        Problem ip = apiConnection.getProblem(id);
        ps.insertProblem(ip);
        return ip;
    }
}
