package com.ssafy.iscode.waitingroom.model.dao;

import com.ssafy.iscode.waitingroom.model.dto.StudyUser;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class StudyUserRepository {

    private final EntityManager em;

    public StudyUserRepository(EntityManager em) {
        this.em = em;
    }

    public int save(StudyUser studyUser) {
        try {
            em.persist(studyUser);
            return 1; // successfully saved
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public StudyUser findById(Long id){
        return em.find(StudyUser.class,id);
    }

    public int remove(Long id){

        StudyUser delUser = findById(id);
        if(delUser != null){
            em.remove(delUser);
            return 1;
            // successfully deleted
        }
        else{
            return 0;
            // failed
        }
    }
}
