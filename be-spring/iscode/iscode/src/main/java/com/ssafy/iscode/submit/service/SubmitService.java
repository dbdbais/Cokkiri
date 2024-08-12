package com.ssafy.iscode.submit.service;

import com.ssafy.iscode.submit.model.dao.SubmitRepository;
import com.ssafy.iscode.submit.model.dto.Submit;
import com.ssafy.iscode.submit.model.dto.SubmitRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubmitService {

    private final SubmitRepository submitRepository;

    @Autowired
    public SubmitService(SubmitRepository submitRepository) {
        this.submitRepository = submitRepository;
    }

    public int insertSubmit(Submit submit)
    {
        return submitRepository.save(submit);
    }

    public int getSolvedProblem(String userId){
        return submitRepository.getSolvedProblem(userId);
    }

    public boolean isReview(String userId, Long problemId){
        return submitRepository.canReview(userId,problemId);
    }

}
