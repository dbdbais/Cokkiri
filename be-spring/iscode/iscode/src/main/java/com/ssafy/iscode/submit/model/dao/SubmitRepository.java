package com.ssafy.iscode.submit.model.dao;

import com.ssafy.iscode.submit.model.dto.Submit;
import com.ssafy.iscode.user.model.dto.User;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class SubmitRepository {
    private final EntityManager em;

    public SubmitRepository(EntityManager em) {
        this.em = em;
    }

    public int save(Submit submit){
        try {

            if(findById(submit.getNo()) == null){
                //Insert
                em.persist(submit);
            }
            else{
                //Modify
                em.merge(submit);
            }
            return 1; // successfully saved
        } catch (Exception e) {
            e.printStackTrace();
            return 0; // failed
        }
    }
    public Submit findById(Long no){
        return em.find(Submit.class,no);
    }









}
