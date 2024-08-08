package com.ssafy.iscode.problem.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssafy.iscode.problem.model.dao.ProblemRepository;
import com.ssafy.iscode.problem.model.dto.Problem;
import com.ssafy.iscode.util.APIConnection;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ProblemService {

    private final ProblemRepository problemRepository;
    private final APIConnection apiConnection;

    @Autowired
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
            String plainText = Jsoup.parse(newProblem.getInfo()).text();
            System.out.println(plainText);
            //fetch hidden test case
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

    public int getHiddenTestCase(Long pid, String jsonData) {
        return problemRepository.getHiddenTC(pid,jsonData);
    }

}
