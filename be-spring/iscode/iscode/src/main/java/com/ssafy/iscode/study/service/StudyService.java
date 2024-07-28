package com.ssafy.iscode.study.service;

import com.ssafy.iscode.study.model.dao.StudyRepository;
import com.ssafy.iscode.study.model.dto.Study;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudyService {
    private final StudyRepository studyRepository;


    public StudyService(StudyRepository studyRepository) {
        this.studyRepository = studyRepository;
    }

    public List<Study> getStudies(){
        return studyRepository.findAll();
    }
    public Study getStudy(Long id){
        return studyRepository.findById(id);
    }

    public int insertStudy(Study study){
        return studyRepository.save(study);
    }

    public int deleteStudy(Long id){
        return studyRepository.remove(id);
    }
}
