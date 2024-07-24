package com.ssafy.iscode.problem.service;

import com.ssafy.iscode.problem.model.dao.ProblemRepository;
import com.ssafy.iscode.problem.model.dto.Problem;
import com.ssafy.iscode.util.APIConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProblemService {

    @Autowired
    private ProblemRepository problemRepository;


    public ProblemService(ProblemRepository problemRepository, APIConnection apiConnection) {
        this.problemRepository = problemRepository;
    }

    public Problem insertProblem(Problem p){
        return problemRepository.save(p);
    }

    public List<Problem> getAllProblem(){
        return problemRepository.findAll();
    }
}
