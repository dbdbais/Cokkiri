package com.ssafy.iscode.problem.controller;

import com.ssafy.iscode.problem.model.dto.Problem;
import com.ssafy.iscode.problem.service.ProblemService;
import com.ssafy.iscode.util.APIConnection;
import jakarta.annotation.Nullable;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/problemapi")
public class ProblemRestController {

    private final ProblemService ps;
    private APIConnection apiConnection;

    @Autowired
    public ProblemRestController(ProblemService ps, APIConnection apiConnection) {
        this.ps = ps;
        this.apiConnection = apiConnection;
    }

    @GetMapping("/all")
    public List<Problem> getProblems(){
        //if there is no id fetch all problem
        return ps.getAllProblem();
    }

    @GetMapping("{pid}")
    public Problem getProblem(@PathVariable Long pid){
        return ps.getProblem(pid);
    }
    //insert the problem
    @PostMapping("/insert")
    public String insert(@RequestParam Long id) throws IOException {
        return ps.insertProblem(id).toString();
    }

    @GetMapping("/hint/{pid}")
    public String getHint(@PathVariable Long pid) throws IOException {

        if(ps.getProblem(pid) == null){
          insert(pid);
          //add the problem
        }
        String probInfo = ps.getProblem(pid).getInfo();
        //get HTML
        String plainText = Jsoup.parse(probInfo).text();
        // get only TEXT
        System.out.println(plainText);
        return apiConnection.useOpenAI(plainText,"너는 알고리즘을 가르쳐주는 선생님이야, 정답을 알려주지 말고 힌트만 제공해줘");
    }

}
