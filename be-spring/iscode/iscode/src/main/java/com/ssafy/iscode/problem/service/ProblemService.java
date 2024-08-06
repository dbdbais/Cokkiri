package com.ssafy.iscode.problem.service;

import com.ssafy.iscode.problem.model.dao.ProblemRepository;
import com.ssafy.iscode.problem.model.dto.Problem;
import com.ssafy.iscode.util.APIConnection;
import com.ssafy.iscode.util.WebCrawler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ProblemService {

    private final ProblemRepository problemRepository;
    private final APIConnection apiConnection;
    
    public ProblemService(ProblemRepository problemRepository, APIConnection apiConnection) {
        this.problemRepository = problemRepository;
        this.apiConnection = apiConnection;
    }

    public Long insertProblem(Long id) throws IOException {

        Problem fp = problemRepository.findById(id);
        //find the database
        if(fp == null){
            //if there is no Problem in database
            Problem newProblem = apiConnection.getProblem(id);
            //fetch problem using Crawler and API
            problemRepository.save(newProblem);
            return newProblem.getNo();
        }
        else{
            //if there is a problem in database
            return fp.getNo();
        }

    }

    public Problem getProblem(Long id){
        return problemRepository.findById(id);
    }

    public List<Problem> getAllProblem(){
        return problemRepository.findAll();
    }

}
