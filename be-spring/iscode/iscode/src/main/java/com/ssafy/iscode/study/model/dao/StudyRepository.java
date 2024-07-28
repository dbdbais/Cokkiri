package com.ssafy.iscode.study.model.dao;

import com.ssafy.iscode.study.model.dto.Study;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class StudyRepository {
    private final EntityManager em;

    public StudyRepository(EntityManager em) {
        this.em = em;
    }

    public Study findById(Long id){
        return em.find(Study.class,id);
    }

    public int save(Study study){
        if(findById(study.getId()) == null){
            em.persist(study);
            return 1;
        }
        else{
            return 0;
        }
    }

    public int remove(Long id){
        Study delStudy = findById(id);
        if(delStudy != null){
            em.remove(delStudy);
            return 1;
            // successfully deleted
        }
        else{
            return 0;
            //failed
        }
    }
    public List<Study> findAll(){
        return em.createQuery("SELECT s FROM Study s", Study.class)
                .getResultList();
    }
}
