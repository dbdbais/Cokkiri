package com.ssafy.iscode.submit.service;

import com.ssafy.iscode.submit.model.dao.SubmitRepository;
import com.ssafy.iscode.submit.model.dto.Submit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubmitService {

    private final SubmitRepository submitRepository;


    @Autowired
    public SubmitService(SubmitRepository submitRepository) {
        this.submitRepository = submitRepository;
    }

    public int insertSubmit(Submit submit){
        return submitRepository.save(submit);
    }

    //must add view and
}
