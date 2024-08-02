package com.ssafy.iscode.submit.controller;

import com.ssafy.iscode.submit.model.dto.Submit;
import com.ssafy.iscode.submit.service.SubmitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/submitapi")
public class SubmitRestController {

    private final SubmitService submitService;

    @Autowired
    public SubmitRestController(SubmitService submitService) {
        this.submitService = submitService;
    }

    @PostMapping
    public int insert(@RequestBody Submit submit){
        return submitService.insertSubmit(submit);
    }



}
