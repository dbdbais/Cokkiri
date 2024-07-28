package com.ssafy.iscode.study.controller;

import com.ssafy.iscode.study.model.dto.Study;
import com.ssafy.iscode.study.service.StudyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/studyapi")
public class StudyRestController {

    private StudyService studyService;

    @Autowired
    public StudyRestController(StudyService studyService) {
        this.studyService = studyService;
    }

    @PostMapping("/register")
    public int regist(@RequestBody Study study){
        return studyService.insertStudy(study);
    }

    @GetMapping()
    public List<Study> getAllStudies(){
        return studyService.getStudies();
    }

    @GetMapping("{id}")
    public Study getStudy(@PathVariable Long id){
        return studyService.getStudy(id);
    }

    @PutMapping("/modify")
    public int modify(@RequestBody Study study){
        return studyService.insertStudy(study);
    }

    @DeleteMapping("/delete/{id}")
    public int delStudy(@PathVariable Long id){
        return studyService.deleteStudy(id);
    }




}
